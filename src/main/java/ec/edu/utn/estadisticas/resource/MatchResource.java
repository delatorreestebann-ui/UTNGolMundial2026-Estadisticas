package ec.edu.utn.estadisticas.resource;

import ec.edu.utn.estadisticas.dto.MatchDTO;
import ec.edu.utn.estadisticas.dto.MatchInputDTO;
import ec.edu.utn.estadisticas.dto.ResultDTO;
import ec.edu.utn.estadisticas.service.StatisticsService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 * REST resource for tournament matches.
 *
 * GET  /api/partidos                    → All matches (full calendar)
 * GET  /api/partidos?fase=GRUPOS        → Matches filtered by phase
 * GET  /api/partidos/{id}               → Match detail (RF08)
 * PUT  /api/partidos/{id}/resultado     → Register official result (RF11)
 * POST /api/partidos                    → Create knockout-stage match (RF10)
 * PUT  /api/partidos/{id}               → Update match data (RF10)
 */
@Path("/partidos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MatchResource {

    @Inject
    private StatisticsService service;

    /** RF04 / RF09 — Full calendar or filtered by phase. */
    @GET
    public Response getMatches(@QueryParam("fase") String phase) {
        List<MatchDTO> matches = (phase != null && !phase.isBlank())
            ? service.getMatchesByPhase(phase)
            : service.getMatches();
        return Response.ok(matches).build();
    }

    /** RF08 — Match detail. */
    @GET
    @Path("/{id}")
    public Response getMatch(@PathParam("id") Integer id) {
        MatchDTO m = service.getMatch(id);
        return m != null ? Response.ok(m).build()
                         : Response.status(Response.Status.NOT_FOUND)
                                   .entity("{\"error\":\"Partido no encontrado\"}")
                                   .build();
    }

    /**
     * RF11 — Register the official result of a finished match.
     * Updates the score, recalculates standings and notifies UTNGolCoin.
     *
     * Example body: { "homeGoals": 2, "awayGoals": 1 }
     */
    @PUT
    @Path("/{id}/resultado")
    public Response registerResult(@PathParam("id") Integer id, ResultDTO dto) {
        if (dto == null || dto.homeGoals == null || dto.awayGoals == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("{\"error\":\"golesLocal y golesVisitante son obligatorios\"}")
                           .build();
        }
        if (dto.homeGoals < 0 || dto.awayGoals < 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("{\"error\":\"Los goles no pueden ser negativos\"}")
                           .build();
        }
        MatchDTO result = service.registerResult(id, dto);
        return result != null ? Response.ok(result).build()
                              : Response.status(Response.Status.NOT_FOUND)
                                        .entity("{\"error\":\"Partido no encontrado\"}")
                                        .build();
    }

    /**
     * RF10 — Create a knockout-stage match from the admin panel.
     * E.g.: round of 16, quarterfinals, semifinal, third place, final.
     */
    @POST
    public Response createMatch(MatchInputDTO dto) {
        if (dto == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("{\"error\":\"Cuerpo de la solicitud inválido\"}")
                           .build();
        }
        MatchDTO created = service.createMatch(dto);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    /**
     * RF10 — Update match data (venue, time, odds, teams for knockout stage).
     */
    @PUT
    @Path("/{id}")
    public Response updateMatch(@PathParam("id") Integer id, MatchInputDTO dto) {
        MatchDTO updated = service.updateMatch(id, dto);
        return updated != null ? Response.ok(updated).build()
                               : Response.status(Response.Status.NOT_FOUND)
                                         .entity("{\"error\":\"Partido no encontrado\"}")
                                         .build();
    }
}