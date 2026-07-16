package ec.edu.utn.estadisticas.resource;

import ec.edu.utn.estadisticas.dto.GroupDTO;
import ec.edu.utn.estadisticas.dto.MatchDTO;
import ec.edu.utn.estadisticas.service.StatisticsService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 * REST resource for the tournament's 12 groups.
 *
 * GET /api/grupos                     → Group listing (RF05)
 * GET /api/grupos/{id}/posiciones     → Group standings table (RF05, RF06)
 * GET /api/grupos/{id}/partidos       → Matches of the group (RF04)
 */
@Path("/grupos")
@Produces(MediaType.APPLICATION_JSON)
public class GroupResource {

    @Inject
    private StatisticsService service;

    /** RF05 — Listing of the 12 groups. */
    @GET
    public Response getGroups() {
        List<GroupDTO> groups = service.getGroups();
        return Response.ok(groups).build();
    }

    /** RF05 / RF06 — Group standings table sorted by points. */
    @GET
    @Path("/{id}/posiciones")
    public Response getStandings(@PathParam("id") Integer id) {
        GroupDTO g = service.getGroupStandings(id);
        return g != null ? Response.ok(g).build()
                         : Response.status(Response.Status.NOT_FOUND)
                                   .entity("{\"error\":\"Grupo no encontrado\"}")
                                   .build();
    }

    /** RF04 — Matches of a specific group (for the frontend calendar). */
    @GET
    @Path("/{id}/partidos")
    public Response getGroupMatches(@PathParam("id") Integer id) {
        List<MatchDTO> matches = service.getMatchesByGroup(id);
        return Response.ok(matches).build();
    }
}