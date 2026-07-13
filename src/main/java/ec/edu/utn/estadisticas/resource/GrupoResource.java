package ec.edu.utn.estadisticas.resource;

import ec.edu.utn.estadisticas.dto.GrupoDTO;
import ec.edu.utn.estadisticas.dto.PartidoDTO;
import ec.edu.utn.estadisticas.service.EstadisticasService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 * Recurso REST para los 12 grupos del torneo.
 *
 * GET /api/grupos                     → Listado de grupos (RF05)
 * GET /api/grupos/{id}/posiciones     → Tabla de posiciones del grupo (RF05, RF06)
 * GET /api/grupos/{id}/partidos       → Partidos del grupo (RF04)
 */
@Path("/grupos")
@Produces(MediaType.APPLICATION_JSON)
public class GrupoResource {

    @Inject
    private EstadisticasService service;

    /** RF05 — Listado de los 12 grupos. */
    @GET
    public Response getGrupos() {
        List<GrupoDTO> grupos = service.getGrupos();
        return Response.ok(grupos).build();
    }

    /** RF05 / RF06 — Tabla de posiciones del grupo ordenada por puntos. */
    @GET
    @Path("/{id}/posiciones")
    public Response getPosiciones(@PathParam("id") Integer id) {
        GrupoDTO g = service.getGrupoPosiciones(id);
        return g != null ? Response.ok(g).build()
                         : Response.status(Response.Status.NOT_FOUND)
                                   .entity("{\"error\":\"Grupo no encontrado\"}")
                                   .build();
    }

    /** RF04 — Partidos de un grupo específico (para el calendario del frontend). */
    @GET
    @Path("/{id}/partidos")
    public Response getPartidosGrupo(@PathParam("id") Integer id) {
        List<PartidoDTO> partidos = service.getPartidosByGrupo(id);
        return Response.ok(partidos).build();
    }
}
