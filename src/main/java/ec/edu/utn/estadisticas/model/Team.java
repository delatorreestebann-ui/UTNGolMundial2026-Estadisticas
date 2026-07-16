package ec.edu.utn.estadisticas.model;
import jakarta.persistence.*;

@Entity
@Table(name = "seleccion")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idseleccion")
    private Integer idTeam;

    @Column(name = "nombre")
    private String name;

    @Column(name = "codigofifa")
    private String fifaCode;

    @Column(name = "esanfitrion")
    private Boolean isHost;

    @Column(name = "clasificacion")
    private String qualification;

    @Column(name = "partidosjugados")
    private Integer matchesPlayed = 0;

    @Column(name = "puntos")
    private Integer points = 0;

    @Column(name = "partidosganados")
    private Integer wins = 0;

    @Column(name = "partidosempatados")
    private Integer draws = 0;

    @Column(name = "partidosperdidos")
    private Integer losses = 0;

    @Column(name = "golesfavor")
    private Integer goalsFor = 0;

    @Column(name = "golescontra")
    private Integer goalsAgainst = 0;

    @ManyToOne
    @JoinColumn(name = "idgrupo")
    private TournamentGroup group;

    @ManyToOne
    @JoinColumn(name = "idconfederacion")
    private Confederation confederation;

    public Integer getIdTeam() { return idTeam; }
    public void setIdTeam(Integer idTeam) { this.idTeam = idTeam; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getFifaCode() { return fifaCode; }
    public void setFifaCode(String fifaCode) { this.fifaCode = fifaCode; }
    public Boolean getIsHost() { return isHost; }
    public void setIsHost(Boolean isHost) { this.isHost = isHost; }
    public String getQualification() { return qualification; }
    public void setQualification(String qualification) { this.qualification = qualification; }
    public Integer getMatchesPlayed() { return matchesPlayed; }
    public void setMatchesPlayed(Integer matchesPlayed) { this.matchesPlayed = matchesPlayed; }
    public Integer getPoints() { return points; }
    public void setPoints(Integer points) { this.points = points; }
    public Integer getWins() { return wins; }
    public void setWins(Integer wins) { this.wins = wins; }
    public Integer getDraws() { return draws; }
    public void setDraws(Integer draws) { this.draws = draws; }
    public Integer getLosses() { return losses; }
    public void setLosses(Integer losses) { this.losses = losses; }
    public Integer getGoalsFor() { return goalsFor; }
    public void setGoalsFor(Integer goalsFor) { this.goalsFor = goalsFor; }
    public Integer getGoalsAgainst() { return goalsAgainst; }
    public void setGoalsAgainst(Integer goalsAgainst) { this.goalsAgainst = goalsAgainst; }
    public TournamentGroup getGroup() { return group; }
    public void setGroup(TournamentGroup group) { this.group = group; }
    public Confederation getConfederation() { return confederation; }
    public void setConfederation(Confederation confederation) { this.confederation = confederation; }
}