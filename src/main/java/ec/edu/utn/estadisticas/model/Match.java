package ec.edu.utn.estadisticas.model;
import jakarta.persistence.*;
import java.util.Date;
import java.math.BigDecimal;

@Entity
@Table(name = "partido")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idpartido")
    private Integer idMatch;

    @Column(name = "numeropartidofifa")
    private Integer fifaMatchNumber;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fechahorautc")
    private Date matchDateTimeUtc;

    @Column(name = "estado")
    private String status;

    @Column(name = "goleslocal")
    private Integer homeGoals;

    @Column(name = "golesvisitante")
    private Integer awayGoals;

    @Column(name = "cuotaempate")
    private BigDecimal drawOdds;

    @Column(name = "cuotavisitante")
    private BigDecimal awayOdds;

    @Column(name = "cuotalocal")
    private BigDecimal homeOdds;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecharesultadoregistrado")
    private Date resultRegisteredAt;

    @ManyToOne
    @JoinColumn(name = "idfase")
    private Phase phase;

    @ManyToOne
    @JoinColumn(name = "idsede")
    private Venue venue;

    @ManyToOne
    @JoinColumn(name = "idgrupo")
    private TournamentGroup group;

    @ManyToOne
    @JoinColumn(name = "idseleccionlocal")
    private Team homeTeam;

    @ManyToOne
    @JoinColumn(name = "idseleccionvisitante")
    private Team awayTeam;

    public Integer getIdMatch() { return idMatch; }
    public void setIdMatch(Integer idMatch) { this.idMatch = idMatch; }
    public Integer getFifaMatchNumber() { return fifaMatchNumber; }
    public void setFifaMatchNumber(Integer fifaMatchNumber) { this.fifaMatchNumber = fifaMatchNumber; }
    public Date getMatchDateTimeUtc() { return matchDateTimeUtc; }
    public void setMatchDateTimeUtc(Date matchDateTimeUtc) { this.matchDateTimeUtc = matchDateTimeUtc; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Integer getHomeGoals() { return homeGoals; }
    public void setHomeGoals(Integer homeGoals) { this.homeGoals = homeGoals; }
    public Integer getAwayGoals() { return awayGoals; }
    public void setAwayGoals(Integer awayGoals) { this.awayGoals = awayGoals; }
    public BigDecimal getDrawOdds() { return drawOdds; }
    public void setDrawOdds(BigDecimal drawOdds) { this.drawOdds = drawOdds; }
    public BigDecimal getAwayOdds() { return awayOdds; }
    public void setAwayOdds(BigDecimal awayOdds) { this.awayOdds = awayOdds; }
    public BigDecimal getHomeOdds() { return homeOdds; }
    public void setHomeOdds(BigDecimal homeOdds) { this.homeOdds = homeOdds; }
    public Date getResultRegisteredAt() { return resultRegisteredAt; }
    public void setResultRegisteredAt(Date resultRegisteredAt) { this.resultRegisteredAt = resultRegisteredAt; }
    public Phase getPhase() { return phase; }
    public void setPhase(Phase phase) { this.phase = phase; }
    public Venue getVenue() { return venue; }
    public void setVenue(Venue venue) { this.venue = venue; }
    public TournamentGroup getGroup() { return group; }
    public void setGroup(TournamentGroup group) { this.group = group; }
    public Team getHomeTeam() { return homeTeam; }
    public void setHomeTeam(Team homeTeam) { this.homeTeam = homeTeam; }
    public Team getAwayTeam() { return awayTeam; }
    public void setAwayTeam(Team awayTeam) { this.awayTeam = awayTeam; }
}