package ec.edu.utn.estadisticas.resource;

import ec.edu.utn.estadisticas.dto.SeleccionDTO;
import ec.edu.utn.estadisticas.service.EstadisticasService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 * Recurso REST para las 48 selecciones participantes del torneo.
 *
 * GET /api/selecciones        → Todas las selecciones con estadísticas (RF07)
 * GET /api/selecciones/{id}   → Estadísticas completas de una selección (RF07)
 */
@Path("/selecciones")
@Produces(MediaType.APPLICATION_JSON)
public class SeleccionResource {

    @Inject
    private EstadisticasService service;

    /** RF07 — Listado completo de selecciones con sus estadísticas. */
    @GET
    public Response getSelecciones() {
        List<SeleccionDTO> selecciones = service.getSelecciones();
        return Response.ok(selecciones).build();
    }

    /** RF07 — Estadísticas completas de una selección específica. */
    @GET
    @Path("/{id}")
    public Response getSeleccion(@PathParam("id") Integer id) {
        SeleccionDTO s = service.getSeleccion(id);
        return s != null ? Response.ok(s).build()
                         : Response.status(Response.Status.NOT_FOUND)
                                   .entity("{\"error\":\"Selección no encontrada\"}")
                                   .build();
    }
}
