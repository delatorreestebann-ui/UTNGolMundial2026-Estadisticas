package ec.edu.utn.estadisticas.resource;

import ec.edu.utn.estadisticas.dto.LogoutDTO;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


// Esta API usa Basic Auth así que
// no hay nada que cerrar en este lado. El cierre real pasa en el
// frontend, cuando borra el email y password que tenía guardado.
@Path("/logout")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LogoutResource {

    @POST
    public Response logout(LogoutDTO dto) {
        String email = (dto != null && dto.email != null) ? dto.email : "desconocido";
        return Response.ok("{\"message\":\"Sesión cerrada para " + email + "\"}").build();
    }
}
