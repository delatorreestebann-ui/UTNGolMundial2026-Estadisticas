package ec.edu.utn.estadisticas.resource;

import ec.edu.utn.estadisticas.dto.TeamDTO;
import ec.edu.utn.estadisticas.service.StatisticsService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 * REST resource for the tournament's 48 participating teams.
 *
 * GET /api/selecciones        → All teams with their statistics (RF07)
 * GET /api/selecciones/{id}   → Full statistics for one team (RF07)
 */
@Path("/selecciones")
@Produces(MediaType.APPLICATION_JSON)
public class TeamResource {

    @Inject
    private StatisticsService service;

    /** RF07 — Full listing of teams with their statistics. */
    @GET
    public Response getTeams() {
        List<TeamDTO> teams = service.getTeams();
        return Response.ok(teams).build();
    }

    /** RF07 — Full statistics for a specific team. */
    @GET
    @Path("/{id}")
    public Response getTeam(@PathParam("id") Integer id) {
        TeamDTO t = service.getTeam(id);
        return t != null ? Response.ok(t).build()
                         : Response.status(Response.Status.NOT_FOUND)
                                   .entity("{\"error\":\"Selección no encontrada\"}")
                                   .build();
    }
}