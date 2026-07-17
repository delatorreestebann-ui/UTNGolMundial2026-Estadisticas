package ec.edu.utn.estadisticas.resource;

import ec.edu.utn.estadisticas.dto.LogoutDTO;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * POST /api/logout → RF03.
 *
 * Importante: esta API usa Basic Auth (sin sesiones ni tokens), por lo que
 * no existe nada del lado del servidor que "invalidar". El cierre de sesión
 * real ocurre en el frontend, al descartar las credenciales que tenía guardadas.
 * Este endpoint existe por completitud del requisito y como punto de enganche
 * para registrar el evento en la auditoría (RF24, paso 6).
 */
@Path("/logout")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LogoutResource {

    @POST
    public Response logout(LogoutDTO dto) {
        String username = (dto != null && dto.username != null) ? dto.username : "desconocido";
        return Response.ok("{\"message\":\"Sesión cerrada para " + username + "\"}").build();
    }
}
