package ec.edu.utn.estadisticas.service;

import jakarta.enterprise.context.ApplicationScoped;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Notifies the UTNGolCoin service when an official result is registered,
 * so it can proceed with the automatic settlement of predictions (RF12).
 *
 * The base URL of the UTNGolCoin service is configured via the environment
 * variable UTNGOLCOIN_URL (e.g. http://localhost:8081/utnGolCoin-backend).
 * If not set, a default development URL is used.
 */
@ApplicationScoped
public class NotificationService {

    private static final Logger LOG = Logger.getLogger(NotificationService.class.getName());

    /** Base URL of the UTNGolCoin service. Configurable via environment variable. */
    private static final String UTNGOLCOIN_URL =
        System.getenv("UTNGOLCOIN_URL") != null
            ? System.getenv("UTNGOLCOIN_URL")
            : "http://localhost:8081/utnGolCoin-backend";

    private final HttpClient httpClient = HttpClient.newBuilder()
        .connectTimeout(Duration.ofSeconds(5))
        .build();

    /**
     * Notifies the UTNGolCoin service that the given match now has an
     * official result registered, so it can settle predictions.
     *
     * @param idMatch ID of the match with the registered result.
     */
    public void notifyResult(Integer idMatch) {
        String url = UTNGOLCOIN_URL + "/api/predicciones/liquidar";
        String body = "{\"idPartido\": " + idMatch + "}";

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(body))
            .timeout(Duration.ofSeconds(10))
            .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                LOG.info("UTNGolCoin notified successfully for match " + idMatch
                    + " — Status: " + response.statusCode());
            } else {
                LOG.warning("UTNGolCoin responded with an error for match " + idMatch
                    + " — Status: " + response.statusCode() + " — Body: " + response.body());
            }
        } catch (Exception e) {
            // Graceful degradation: if UTNGolCoin is unavailable, the result
            // is still saved. The error is logged for audit purposes (RNF05).
            LOG.log(Level.SEVERE,
                "Could not notify UTNGolCoin for match " + idMatch
                + ". The result was saved anyway. Error: " + e.getMessage(), e);
        }
    }
}