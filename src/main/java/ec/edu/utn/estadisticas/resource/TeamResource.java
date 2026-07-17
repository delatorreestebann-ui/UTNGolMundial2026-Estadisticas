package ec.edu.utn.estadisticas.resource;

import ec.edu.utn.estadisticas.dto.TeamDTO;
import ec.edu.utn.estadisticas.dto.TeamInputDTO;
import ec.edu.utn.estadisticas.service.StatisticsService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;


@Path("/selecciones")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
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

    /**
     * RF10 — Crear una nueva selección desde el panel administrativo.
     * Ejemplo de cuerpo: { "name": "Ecuador", "fifaCode": "ECU", "idGroup": 3, "idConfederation": 2 }
     */
    @POST
    public Response createTeam(TeamInputDTO dto) {
        String error = validate(dto);
        if (error != null) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("{\"error\":\"" + error + "\"}")
                           .build();
        }
        TeamDTO created = service.createTeam(dto);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    /**
     * RF10 — Actualizar los datos de una selección existente.
     */
    @PUT
    @Path("/{id}")
    public Response updateTeam(@PathParam("id") Integer id, TeamInputDTO dto) {
        String error = validate(dto);
        if (error != null) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("{\"error\":\"" + error + "\"}")
                           .build();
        }
        TeamDTO updated = service.updateTeam(id, dto);
        return updated != null ? Response.ok(updated).build()
                               : Response.status(Response.Status.NOT_FOUND)
                                         .entity("{\"error\":\"Selección no encontrada\"}")
                                         .build();
    }

    /** RNF10 — Validaciones básicas antes de crear/actualizar una selección. */
    private String validate(TeamInputDTO dto) {
        if (dto == null) {
            return "Cuerpo de la solicitud inválido";
        }
        if (dto.name == null || dto.name.trim().isEmpty()) {
            return "El campo 'name' es obligatorio";
        }
        if (dto.fifaCode == null || dto.fifaCode.trim().isEmpty()) {
            return "El campo 'fifaCode' es obligatorio";
        }
        if (dto.fifaCode.trim().length() != 3) {
            return "El campo 'fifaCode' debe tener 3 caracteres (ej. ECU, MEX, USA)";
        }
        return null;
    }
}
