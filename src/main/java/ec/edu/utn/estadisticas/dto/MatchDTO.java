package ec.edu.utn.estadisticas.dto;
import java.util.Date;
public class MatchDTO {
    public Integer idMatch;
    public Integer fifaMatchNumber;
    public Date matchDateTimeUtc;
    public String status;
    public String homeTeam;
    public String awayTeam;
    public Integer homeGoals;
    public Integer awayGoals;
    public String venue;
    public String group;
    public String phase;
    public java.math.BigDecimal homeOdds;
    public java.math.BigDecimal awayOdds;
    public java.math.BigDecimal drawOdds;
}