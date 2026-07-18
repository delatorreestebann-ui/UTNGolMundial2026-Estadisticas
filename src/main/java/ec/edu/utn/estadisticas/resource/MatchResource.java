package ec.edu.utn.estadisticas.resource;

import ec.edu.utn.estadisticas.dto.MatchDTO;
import ec.edu.utn.estadisticas.dto.MatchInputDTO;
import ec.edu.utn.estadisticas.dto.ResultDTO;
import ec.edu.utn.estadisticas.service.StatisticsService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;


@Path("/partidos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MatchResource {

    @Inject
    private StatisticsService service;

    @GET
    public Response getMatches(@QueryParam("fase") String phase) {
        List<MatchDTO> matches = (phase != null && !phase.isBlank())
            ? service.getMatchesByPhase(phase)
            : service.getMatches();
        return Response.ok(matches).build();
    }

    @GET
    @Path("/{id}")
    public Response getMatch(@PathParam("id") Integer id) {
        MatchDTO m = service.getMatch(id);
        return m != null ? Response.ok(m).build()
                         : Response.status(Response.Status.NOT_FOUND)
                                   .entity("{\"error\":\"Partido no encontrado\"}")
                                   .build();
    }

    @PUT
    @Path("/{id}/resultado")
    @SecurityRequirement(name = "basicAuth")
    public Response registerResult(@PathParam("id") Integer id, ResultDTO dto) {
        if (dto == null || dto.homeGoals == null || dto.awayGoals == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("{\"error\":\"golesLocal y golesVisitante son obligatorios\"}")
                           .build();
        }
        if (dto.homeGoals < 0 || dto.awayGoals < 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("{\"error\":\"Los goles no pueden ser negativos\"}")
                           .build();
        }
        MatchDTO result = service.registerResult(id, dto);
        return result != null ? Response.ok(result).build()
                              : Response.status(Response.Status.NOT_FOUND)
                                        .entity("{\"error\":\"Partido no encontrado\"}")
                                        .build();
    }

 
    @POST
    @SecurityRequirement(name = "basicAuth")
    public Response createMatch(MatchInputDTO dto) {
        if (dto == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("{\"error\":\"Cuerpo de la solicitud inválido\"}")
                           .build();
        }
        MatchDTO created = service.createMatch(dto);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

   
    @PUT
    @Path("/{id}")
    @SecurityRequirement(name = "basicAuth")
    public Response updateMatch(@PathParam("id") Integer id, MatchInputDTO dto) {
        MatchDTO updated = service.updateMatch(id, dto);
        return updated != null ? Response.ok(updated).build()
                               : Response.status(Response.Status.NOT_FOUND)
                                         .entity("{\"error\":\"Partido no encontrado\"}")
                                         .build();
    }
}
