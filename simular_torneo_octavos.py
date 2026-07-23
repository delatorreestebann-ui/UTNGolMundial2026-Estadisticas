"""
Simula el avance del torneo hasta Octavos de Final (sin resultado).

1. Registra resultado a los 72 partidos de fase de grupos.
2. Calcula los 32 clasificados (2 por grupo + 8 mejores terceros).
3. Arma y JUEGA Dieciseisavos (16 partidos, con resultado).
4. Arma Octavos (8 partidos) — SIN RESULTADO, para que el admin los
   complete en vivo durante la demo.

No crea nada de Cuartos en adelante — eso lo hace el admin desde cero.
"""

import json
import random
import urllib.request
import urllib.error
import base64
from datetime import datetime, timedelta, timezone

BASE_URL = "http://localhost:8080/estadisticas-backend/api"
ADMIN_EMAIL = "admin@utngolmundial.edu.ec"
ADMIN_PASSWORD = "admin123"

random.seed(42)


def auth_header():
    token = base64.b64encode(f"{ADMIN_EMAIL}:{ADMIN_PASSWORD}".encode()).decode()
    return f"Basic {token}"


def request(method, path, body=None, auth=False):
    url = BASE_URL + path
    data = json.dumps(body).encode() if body is not None else None
    req = urllib.request.Request(url, data=data, method=method)
    req.add_header("Content-Type", "application/json")
    if auth:
        req.add_header("Authorization", auth_header())
    try:
        with urllib.request.urlopen(req) as resp:
            return json.loads(resp.read().decode())
    except urllib.error.HTTPError as e:
        print(f"  ERROR {method} {path} -> {e.code}: {e.read().decode()}")
        return None


def get(path):
    return request("GET", path)


def post(path, body):
    return request("POST", path, body, auth=True)


def put(path, body):
    return request("PUT", path, body, auth=True)


def random_score(force_decisive=False):
    home = random.choices([0, 1, 2, 3, 4], weights=[15, 30, 30, 15, 10])[0]
    away = random.choices([0, 1, 2, 3, 4], weights=[15, 30, 30, 15, 10])[0]
    if force_decisive:
        while home == away:
            home = random.choices([0, 1, 2, 3, 4], weights=[15, 30, 30, 15, 10])[0]
            away = random.choices([0, 1, 2, 3, 4], weights=[15, 30, 30, 15, 10])[0]
    return home, away


def simulate_group_stage():
    print("=== 1. Simulando resultados de fase de grupos ===")
    matches = get("/partidos")
    group_matches = [m for m in matches if m.get("group") and m.get("status") == "PROGRAMADO"]
    print(f"  {len(group_matches)} partidos de grupos por simular")
    for m in group_matches:
        home, away = random_score()
        result = put(f"/partidos/{m['idMatch']}/resultado", {"homeGoals": home, "awayGoals": away})
        if result:
            print(f"  {m['homeTeam']} {home} - {away} {m['awayTeam']}")


def get_qualifiers():
    print("=== 2. Calculando clasificados ===")
    groups = get("/grupos")
    top2 = []
    thirds = []
    for g in groups:
        standings = get(f"/grupos/{g['idGroup']}/posiciones")["standings"]
        top2.append(standings[0])
        top2.append(standings[1])
        thirds.append(standings[2])
    thirds.sort(key=lambda t: (t["points"], t["goalDifference"], t["goalsFor"]), reverse=True)
    qualifiers = top2 + thirds[:8]
    print(f"  {len(qualifiers)} equipos clasificados a Dieciseisavos")
    return [q["idTeam"] for q in qualifiers]


def get_phase_id(code):
    phases = get("/fases")
    for p in phases:
        if p["code"] == code:
            return p["idPhase"], p["startDate"]
    raise Exception(f"No se encontró la fase {code}")


def get_venue_ids():
    return [v["idVenue"] for v in get("/sedes")]


def play_round(team_ids, phase_code, fifa_start, with_result):
    print(f"=== Armando {phase_code} ({len(team_ids)//2} partidos) ===")
    idPhase, start_ts = get_phase_id(phase_code)
    venue_ids = get_venue_ids()
    match_date = datetime.fromtimestamp(start_ts / 1000, tz=timezone.utc) + timedelta(days=2)
    date_str = match_date.strftime("%Y-%m-%dT18:00:00Z")

    winners = []
    fifa_number = fifa_start
    for i in range(0, len(team_ids), 2):
        home_id, away_id = team_ids[i], team_ids[i + 1]
        body = {
            "fifaMatchNumber": fifa_number,
            "matchDateTimeUtc": date_str,
            "status": "PROGRAMADO",
            "idPhase": idPhase,
            "idVenue": random.choice(venue_ids),
            "idHomeTeam": home_id,
            "idAwayTeam": away_id,
            "homeOdds": round(random.uniform(1.5, 4.0), 2),
            "drawOdds": round(random.uniform(2.8, 3.6), 2),
            "awayOdds": round(random.uniform(1.5, 4.0), 2),
        }
        created = post("/partidos", body)
        if not created:
            continue
        fifa_number += 1

        if with_result:
            home, away = random_score(force_decisive=True)
            put(f"/partidos/{created['idMatch']}/resultado", {"homeGoals": home, "awayGoals": away})
            winner_id = home_id if home > away else away_id
            winners.append(winner_id)
            print(f"  idMatch {created['idMatch']}: {home}-{away} -> gana equipo {winner_id}")
        else:
            print(f"  idMatch {created['idMatch']} creado, SIN resultado (para completar en vivo)")

    return winners


def main():
    login = request("POST", "/login", {"email": ADMIN_EMAIL, "password": ADMIN_PASSWORD})
    if not login:
        print("No se pudo iniciar sesión como admin.")
        return
    print(f"Login OK, rol: {login.get('role')}\n")

    simulate_group_stage()
    qualifier_ids = get_qualifiers()
    r32_winners = play_round(qualifier_ids, "DIECISEISAVOS", fifa_start=101, with_result=True)
    play_round(r32_winners, "OCTAVOS", fifa_start=121, with_result=False)

    print("\n=== LISTO ===")
    print("Grupos y Dieciseisavos: simulados con resultado.")
    print("Octavos: creados, SIN resultado — listos para completar en vivo.")


if __name__ == "__main__":
    main()
