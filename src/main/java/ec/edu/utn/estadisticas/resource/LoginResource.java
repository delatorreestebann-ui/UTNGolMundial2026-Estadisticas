package ec.edu.utn.estadisticas.resource;

import ec.edu.utn.estadisticas.dto.LoginDTO;
import ec.edu.utn.estadisticas.dto.UserDTO;
import ec.edu.utn.estadisticas.service.UserService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

// POST /api/login
// Devuelve los datos del usuario si el email y password son correctos.
// El frontend debe guardar ese email y pasword
// "authorization en las peticiones que necesiten permisos de admin.
@Path("/login")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LoginResource {

    @Inject
    private UserService service;

    @POST
    public Response login(LoginDTO credentials) {
        if (credentials == null || credentials.email == null || credentials.password == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("{\"error\":\"Se requieren email y password\"}")
                           .build();
        }
        UserDTO user = service.login(credentials);
        if (user == null) {
            // Mensaje genérico a propósito: no dice si falló el email o el password.
            return Response.status(Response.Status.UNAUTHORIZED)
                           .entity("{\"error\":\"Credenciales inválidas\"}")
                           .build();
        }
        return Response.ok(user).build();
    }
}
