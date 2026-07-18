package ec.edu.utn.estadisticas.resource;

import ec.edu.utn.estadisticas.dto.UserDTO;
import ec.edu.utn.estadisticas.dto.UserInputDTO;
import ec.edu.utn.estadisticas.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    private UserService service;

    @POST
    public Response registerUser(UserInputDTO dto) {
        String error = service.validateRegistration(dto);
        if (error != null) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("{\"error\":\"" + error + "\"}")
                           .build();
        }
        UserDTO created = service.registerUser(dto);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @GET
    @SecurityRequirement(name = "basicAuth")
    public Response getUsers() {
        List<UserDTO> users = service.listUsers();
        return Response.ok(users).build();
    }

    @GET
    @Path("/{id}")
    @SecurityRequirement(name = "basicAuth")
    public Response getUser(@PathParam("id") Integer id) {
        UserDTO u = service.getUser(id);
        return u != null ? Response.ok(u).build()
                         : Response.status(Response.Status.NOT_FOUND)
                                   .entity("{\"error\":\"Usuario no encontrado\"}")
                                   .build();
    }

    @PUT
    @Path("/{id}")
    @SecurityRequirement(name = "basicAuth")
    public Response updateUser(@PathParam("id") Integer id, UserInputDTO dto) {
        if (dto == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("{\"error\":\"Cuerpo de la solicitud inválido\"}")
                           .build();
        }
        if (dto.password != null && !dto.password.isEmpty() && dto.password.length() < 8) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("{\"error\":\"El campo 'password' debe tener al menos 8 caracteres\"}")
                           .build();
        }
        UserDTO updated = service.updateUser(id, dto);
        return updated != null ? Response.ok(updated).build()
                               : Response.status(Response.Status.NOT_FOUND)
                                         .entity("{\"error\":\"Usuario no encontrado\"}")
                                         .build();
    }
}
