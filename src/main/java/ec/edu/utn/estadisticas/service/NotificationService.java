package ec.edu.utn.estadisticas.service;

import ec.edu.utn.estadisticas.model.Match;
import jakarta.enterprise.context.ApplicationScoped;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
public class NotificationService {

    private static final Logger LOG = Logger.getLogger(NotificationService.class.getName());

    private static final String UTNGOLCOIN_URL =
        System.getenv("UTNGOLCOIN_URL") != null
            ? System.getenv("UTNGOLCOIN_URL")
            : "http://192.168.3.77:57781";


    private final HttpClient httpClient = HttpClient.newBuilder()
        .connectTimeout(Duration.ofSeconds(5))
        .build();

    
    public void notifyResult(Match match) {
        String url = UTNGOLCOIN_URL + "/api/Predictions/settle";
        String officialResult = calculateResult(match);
        BigDecimal appliedOdds = calculateAppliedOdds(match, officialResult);

        String body = String.format(
            "{\"matchId\": %d, \"officialResult\": \"%s\", \"appliedOdds\": %s}",
            match.getIdMatch(), officialResult, appliedOdds);

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(body))
            .timeout(Duration.ofSeconds(10))
            .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                LOG.info("UTNGolCoin notified successfully for match " + match.getIdMatch()
                    + " — Status: " + response.statusCode());
            } else {
                LOG.warning("UTNGolCoin responded with an error for match " + match.getIdMatch()
                    + " — Status: " + response.statusCode() + " — Body: " + response.body());
            }
        } catch (Exception e) {
            
            
            LOG.log(Level.SEVERE,
                "Could not notify UTNGolCoin for match " + match.getIdMatch()
                + ". The result was saved anyway. Error: " + e.getMessage(), e);
        }
    }

    
    private String calculateResult(Match match) {
        int home = match.getHomeGoals() != null ? match.getHomeGoals() : 0;
        int away = match.getAwayGoals() != null ? match.getAwayGoals() : 0;
        if (home > away) return "1";
        if (home < away) return "2";
        return "X";
    }

    
    private BigDecimal calculateAppliedOdds(Match match, String officialResult) {
        BigDecimal odds;
        switch (officialResult) {
            case "1": odds = match.getHomeOdds(); break;
            case "2": odds = match.getAwayOdds(); break;
            default:  odds = match.getDrawOdds(); break;
        }
        return odds != null ? odds : BigDecimal.ZERO;
    }
}
