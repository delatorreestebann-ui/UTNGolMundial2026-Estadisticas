package ec.edu.utn.estadisticas.resource;

import ec.edu.utn.estadisticas.dto.GroupDTO;
import ec.edu.utn.estadisticas.dto.MatchDTO;
import ec.edu.utn.estadisticas.service.StatisticsService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/grupos")
@Produces(MediaType.APPLICATION_JSON)
public class GroupResource {

    @Inject
    private StatisticsService service;

    @GET
    public Response getGroups() {
        List<GroupDTO> groups = service.getGroups();
        return Response.ok(groups).build();
    }

    @GET
    @Path("/{id}/posiciones")
    public Response getStandings(@PathParam("id") Integer id) {
        GroupDTO g = service.getGroupStandings(id);
        return g != null ? Response.ok(g).build()
                         : Response.status(Response.Status.NOT_FOUND)
                                   .entity("{\"error\":\"Grupo no encontrado\"}")
                                   .build();
    }

    @GET
    @Path("/{id}/partidos")
    public Response getGroupMatches(@PathParam("id") Integer id) {
        List<MatchDTO> matches = service.getMatchesByGroup(id);
        return Response.ok(matches).build();
    }
}