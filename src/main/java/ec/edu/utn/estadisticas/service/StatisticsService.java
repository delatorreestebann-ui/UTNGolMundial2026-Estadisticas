package ec.edu.utn.estadisticas.service;

import ec.edu.utn.estadisticas.dto.*;
import ec.edu.utn.estadisticas.model.*;
import ec.edu.utn.estadisticas.repository.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import ec.edu.utn.estadisticas.audit.Auditable;

@ApplicationScoped
public class StatisticsService {

    @Inject
    private MatchRepository matchRepo;
    @Inject
    private GroupRepository groupRepo;
    @Inject
    private TeamRepository teamRepo;
    @Inject
    private NotificationService notificationService;

    

    public List<MatchDTO> getMatches() {
        return matchRepo.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<MatchDTO> getMatchesByPhase(String phaseCode) {
        return matchRepo.findByPhase(phaseCode).stream().map(this::toDTO).collect(Collectors.toList());
    }

    public MatchDTO getMatch(Integer id) {
        Match m = matchRepo.findById(id);
        return m != null ? toDTO(m) : null;
    }

    public List<MatchDTO> getMatchesByGroup(Integer idGroup) {
        return matchRepo.findByGroup(idGroup).stream().map(this::toDTO).collect(Collectors.toList());
    }

    
    
    
    @Transactional
    @Auditable
    public MatchDTO registerResult(Integer idMatch, ResultDTO dto) {
        Match match = matchRepo.findById(idMatch);
        if (match == null) return null;

        
        match.setHomeGoals(dto.homeGoals);
        match.setAwayGoals(dto.awayGoals);
        match.setStatus("FINALIZADO");
        match.setResultRegisteredAt(new Date());
        matchRepo.update(match);

        
        if (match.getGroup() != null) {
            updateTeamStatistics(match.getHomeTeam(), dto.homeGoals, dto.awayGoals);
            updateTeamStatistics(match.getAwayTeam(), dto.awayGoals, dto.homeGoals);
        }

        
        notificationService.notifyResult(match);

        return toDTO(match);
    }

    
    private void updateTeamStatistics(Team t, int goalsFor, int goalsAgainst) {
        if (t == null) return;
        
        
        t.setMatchesPlayed(safe(t.getMatchesPlayed()) + 1);
        t.setGoalsFor(safe(t.getGoalsFor()) + goalsFor);
        t.setGoalsAgainst(safe(t.getGoalsAgainst()) + goalsAgainst);

        
        if (goalsFor > goalsAgainst) {
            t.setWins(safe(t.getWins()) + 1);
            t.setPoints(safe(t.getPoints()) + 3); 
        } else if (goalsFor == goalsAgainst) {
            t.setDraws(safe(t.getDraws()) + 1);
            t.setPoints(safe(t.getPoints()) + 1); 
        } else {
            t.setLosses(safe(t.getLosses()) + 1); 
        }
        teamRepo.update(t);
    }

    private int safe(Integer v) { return v != null ? v : 0; }

    

    @Transactional
    @Auditable
    public MatchDTO createMatch(MatchInputDTO dto) {
        Match m = new Match();
        applyInputDTO(m, dto);
        return toDTO(matchRepo.save(m));
    }

    @Transactional
    public MatchDTO updateMatch(Integer idMatch, MatchInputDTO dto) {
        Match m = matchRepo.findById(idMatch);
        if (m == null) return null;
        applyInputDTO(m, dto);
        return toDTO(matchRepo.update(m));
    }

    private void applyInputDTO(Match m, MatchInputDTO dto) {
        m.setFifaMatchNumber(dto.fifaMatchNumber);
        m.setStatus(dto.status != null ? dto.status : "PROGRAMADO");
        if (dto.homeOdds != null) m.setHomeOdds(BigDecimal.valueOf(dto.homeOdds));
        if (dto.drawOdds != null) m.setDrawOdds(BigDecimal.valueOf(dto.drawOdds));
        if (dto.awayOdds != null) m.setAwayOdds(BigDecimal.valueOf(dto.awayOdds));
        if (dto.matchDateTimeUtc != null) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
                m.setMatchDateTimeUtc(sdf.parse(dto.matchDateTimeUtc));
            } catch (Exception ignored) {}
        }
        if (dto.idPhase != null) { Phase p = new Phase(); p.setIdPhase(dto.idPhase); m.setPhase(p); }
        if (dto.idVenue != null) { Venue v = new Venue(); v.setIdVenue(dto.idVenue); m.setVenue(v); }
        if (dto.idGroup != null) { TournamentGroup g = new TournamentGroup(); g.setIdGroup(dto.idGroup); m.setGroup(g); }
        if (dto.idHomeTeam != null) { Team t = new Team(); t.setIdTeam(dto.idHomeTeam); m.setHomeTeam(t); }
        if (dto.idAwayTeam != null) { Team t = new Team(); t.setIdTeam(dto.idAwayTeam); m.setAwayTeam(t); }
    }

    

    public List<GroupDTO> getGroups() {
        return groupRepo.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    
    public GroupDTO getGroupStandings(Integer id) {
        TournamentGroup g = groupRepo.findById(id);
        if (g == null) return null;
        GroupDTO dto = toDTO(g);

        List<Team> teams = groupRepo.findTeamsByGroup(id);
        
        
        
        
        
        dto.standings = teams.stream()
            .map(this::toStandingDTO)
            .sorted(Comparator.comparingInt((StandingDTO p) -> p.points)
                .thenComparingInt(p -> p.goalDifference)
                .thenComparingInt(p -> p.goalsFor)
                .reversed())
            .collect(Collectors.toList());

        return dto;
    }

    

    public List<TeamDTO> getTeams() {
        return teamRepo.findAll().stream().map(this::toTeamDTO).collect(Collectors.toList());
    }

    public TeamDTO getTeam(Integer id) {
        Team t = teamRepo.findById(id);
        return t != null ? toTeamDTO(t) : null;
    }

    
    @Transactional
    @Auditable
    public TeamDTO createTeam(TeamInputDTO dto) {
        Team t = new Team();
        applyTeamInputDTO(t, dto);
        return toTeamDTO(teamRepo.save(t));
    }

    
    @Transactional
    @Auditable
    public TeamDTO updateTeam(Integer idTeam, TeamInputDTO dto) {
        Team t = teamRepo.findById(idTeam);
        if (t == null) return null;
        applyTeamInputDTO(t, dto);
        return toTeamDTO(teamRepo.update(t));
    }

    private void applyTeamInputDTO(Team t, TeamInputDTO dto) {
        t.setName(dto.name);
        t.setFifaCode(dto.fifaCode);
        t.setIsHost(dto.isHost != null ? dto.isHost : Boolean.FALSE);
        t.setQualification(dto.qualification);
        if (dto.idGroup != null) {
            TournamentGroup g = new TournamentGroup();
            g.setIdGroup(dto.idGroup);
            t.setGroup(g);
        }
        if (dto.idConfederation != null) {
            Confederation c = new Confederation();
            c.setIdConfederation(dto.idConfederation);
            t.setConfederation(c);
        }
    }

    

    private MatchDTO toDTO(Match m) {
        MatchDTO dto = new MatchDTO();
        dto.idMatch = m.getIdMatch();
        dto.fifaMatchNumber = m.getFifaMatchNumber();
        dto.matchDateTimeUtc = m.getMatchDateTimeUtc();
        dto.status = m.getStatus();
        dto.homeTeam = m.getHomeTeam() != null ? m.getHomeTeam().getName() : null;
        dto.awayTeam = m.getAwayTeam() != null ? m.getAwayTeam().getName() : null;
        dto.homeGoals = m.getHomeGoals();
        dto.awayGoals = m.getAwayGoals();
        dto.venue = m.getVenue() != null ? m.getVenue().getStadium() : null;
        dto.group = m.getGroup() != null ? m.getGroup().getName() : null;
        dto.phase = m.getPhase() != null ? m.getPhase().getName() : null;
        dto.homeOdds = m.getHomeOdds();
        dto.awayOdds = m.getAwayOdds();
        dto.drawOdds = m.getDrawOdds();
        return dto;
    }

    private GroupDTO toDTO(TournamentGroup g) {
        GroupDTO dto = new GroupDTO();
        dto.idGroup = g.getIdGroup();
        dto.code = g.getCode();
        dto.name = g.getName();
        return dto;
    }

    private StandingDTO toStandingDTO(Team t) {
        StandingDTO dto = new StandingDTO();
        dto.idTeam = t.getIdTeam();
        dto.name = t.getName();
        dto.fifaCode = t.getFifaCode();
        dto.matchesPlayed = safe(t.getMatchesPlayed());
        dto.points = safe(t.getPoints());
        dto.wins = safe(t.getWins());
        dto.draws = safe(t.getDraws());
        dto.losses = safe(t.getLosses());
        dto.goalsFor = safe(t.getGoalsFor());
        dto.goalsAgainst = safe(t.getGoalsAgainst());
        dto.goalDifference = dto.goalsFor - dto.goalsAgainst;
        return dto;
    }

    private TeamDTO toTeamDTO(Team t) {
        TeamDTO dto = new TeamDTO();
        dto.idTeam = t.getIdTeam();
        dto.name = t.getName();
        dto.fifaCode = t.getFifaCode();
        dto.isHost = t.getIsHost();
        dto.qualification = t.getQualification();
        dto.group = t.getGroup() != null ? t.getGroup().getName() : null;
        dto.confederation = t.getConfederation() != null ? t.getConfederation().getName() : null;
        dto.matchesPlayed = safe(t.getMatchesPlayed());
        dto.points = safe(t.getPoints());
        dto.wins = safe(t.getWins());
        dto.draws = safe(t.getDraws());
        dto.losses = safe(t.getLosses());
        dto.goalsFor = safe(t.getGoalsFor());
        dto.goalsAgainst = safe(t.getGoalsAgainst());
        dto.goalDifference = dto.goalsFor - dto.goalsAgainst;
        return dto;
    }
}
