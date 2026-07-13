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
 * Servicio responsable de notificar al Servicio UTNGolCoin cuando se registra
 * un resultado oficial, para que proceda con la liquidación automática de predicciones (RF12).
 *
 * La URL base del servicio UTNGolCoin se configura como variable de entorno
 * UTNGO_LCOIN_URL (ej: http://localhost:8081/utnGolCoin-backend).
 * Si no está configurada, usa una URL por defecto de desarrollo.
 */
@ApplicationScoped
public class NotificacionService {

    private static final Logger LOG = Logger.getLogger(NotificacionService.class.getName());

    /** URL base del Servicio UTNGolCoin. Configurable por variable de entorno. */
    private static final String UTNGO_LCOIN_URL =
        System.getenv("UTNGO_LCOIN_URL") != null
            ? System.getenv("UTNGO_LCOIN_URL")
            : "http://localhost:8081/utnGolCoin-backend";

    private final HttpClient httpClient = HttpClient.newBuilder()
        .connectTimeout(Duration.ofSeconds(5))
        .build();

    /**
     * Notifica al Servicio UTNGolCoin que el partido con el ID dado ya tiene
     * un resultado oficial registrado, para que proceda a liquidar predicciones.
     *
     * @param idPartido ID del partido con resultado registrado.
     */
    public void notificarResultado(Integer idPartido) {
        String url = UTNGO_LCOIN_URL + "/api/predicciones/liquidar";
        String body = "{\"idPartido\": " + idPartido + "}";

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(body))
            .timeout(Duration.ofSeconds(10))
            .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                LOG.info("UTNGolCoin notificado correctamente para partido " + idPartido
                    + " — Status: " + response.statusCode());
            } else {
                LOG.warning("UTNGolCoin respondió con error para partido " + idPartido
                    + " — Status: " + response.statusCode() + " — Body: " + response.body());
            }
        } catch (Exception e) {
            // Degradación controlada: si UTNGolCoin no está disponible, el resultado
            // se guarda de todas formas. Se registra el error para auditoría (RNF05).
            LOG.log(Level.SEVERE,
                "No se pudo notificar a UTNGolCoin para partido " + idPartido
                + ". El resultado fue guardado igualmente. Error: " + e.getMessage(), e);
        }
    }
}
