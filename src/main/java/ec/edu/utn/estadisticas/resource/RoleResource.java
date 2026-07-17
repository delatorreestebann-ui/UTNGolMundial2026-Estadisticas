package ec.edu.utn.estadisticas.resource;

import ec.edu.utn.estadisticas.model.Role;
import ec.edu.utn.estadisticas.repository.RoleRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;


@Path("/roles")
@Produces(MediaType.APPLICATION_JSON)
public class RoleResource {

    @Inject
    private RoleRepository repo;

    @GET
    public Response getRoles() {
        List<Role> roles = repo.findAll();
        return Response.ok(roles).build();
    }

    @GET
    @Path("/{id}")
    public Response getRole(@PathParam("id") Integer id) {
        Role r = repo.findById(id);
        return r != null ? Response.ok(r).build()
                         : Response.status(Response.Status.NOT_FOUND)
                                   .entity("{\"error\":\"Rol no encontrado\"}")
                                   .build();
    }
}
