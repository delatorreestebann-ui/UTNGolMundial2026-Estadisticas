package ec.edu.utn.estadisticas.resource;

import ec.edu.utn.estadisticas.dto.PartidoDTO;
import ec.edu.utn.estadisticas.dto.PartidoInputDTO;
import ec.edu.utn.estadisticas.dto.ResultadoDTO;
import ec.edu.utn.estadisticas.service.EstadisticasService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 * Recurso REST para partidos del torneo.
 *
 * GET  /api/partidos                    → Todos los partidos (calendario completo)
 * GET  /api/partidos?fase=GRUPOS        → Partidos filtrados por fase
 * GET  /api/partidos/{id}               → Detalle de un partido (RF08)
 * PUT  /api/partidos/{id}/resultado     → Registrar resultado oficial (RF11)
 * POST /api/partidos                    → Crear partido eliminatorio (RF10)
 * PUT  /api/partidos/{id}               → Actualizar datos de un partido (RF10)
 */
@Path("/partidos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PartidoResource {

    @Inject
    private EstadisticasService service;

    /** RF04 / RF09 — Calendario completo o filtrado por fase. */
    @GET
    public Response getPartidos(@QueryParam("fase") String fase) {
        List<PartidoDTO> partidos = (fase != null && !fase.isBlank())
            ? service.getPartidosByFase(fase)
            : service.getPartidos();
        return Response.ok(partidos).build();
    }

    /** RF08 — Detalle de un partido. */
    @GET
    @Path("/{id}")
    public Response getPartido(@PathParam("id") Integer id) {
        PartidoDTO p = service.getPartido(id);
        return p != null ? Response.ok(p).build()
                         : Response.status(Response.Status.NOT_FOUND)
                                   .entity("{\"error\":\"Partido no encontrado\"}")
                                   .build();
    }

    /**
     * RF11 — Registrar resultado oficial de un partido finalizado.
     * Actualiza marcador, recalcula posiciones y notifica a UTNGolCoin.
     *
     * Ejemplo body: { "golesLocal": 2, "golesVisitante": 1 }
     */
    @PUT
    @Path("/{id}/resultado")
    public Response registrarResultado(@PathParam("id") Integer id, ResultadoDTO dto) {
        if (dto == null || dto.golesLocal == null || dto.golesVisitante == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("{\"error\":\"golesLocal y golesVisitante son obligatorios\"}")
                           .build();
        }
        if (dto.golesLocal < 0 || dto.golesVisitante < 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("{\"error\":\"Los goles no pueden ser negativos\"}")
                           .build();
        }
        PartidoDTO resultado = service.registrarResultado(id, dto);
        return resultado != null ? Response.ok(resultado).build()
                                 : Response.status(Response.Status.NOT_FOUND)
                                           .entity("{\"error\":\"Partido no encontrado\"}")
                                           .build();
    }

    /**
     * RF10 — Crear un partido de fase eliminatoria desde el panel admin.
     * Ejemplo: octavos, cuartos, semifinal, tercer puesto, final.
     */
    @POST
    public Response crearPartido(PartidoInputDTO dto) {
        if (dto == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("{\"error\":\"Cuerpo de la solicitud inválido\"}")
                           .build();
        }
        PartidoDTO creado = service.crearPartido(dto);
        return Response.status(Response.Status.CREATED).entity(creado).build();
    }

    /**
     * RF10 — Actualizar datos de un partido (sede, hora, cuotas, selecciones para eliminatorias).
     */
    @PUT
    @Path("/{id}")
    public Response actualizarPartido(@PathParam("id") Integer id, PartidoInputDTO dto) {
        PartidoDTO actualizado = service.actualizarPartido(id, dto);
        return actualizado != null ? Response.ok(actualizado).build()
                                   : Response.status(Response.Status.NOT_FOUND)
                                             .entity("{\"error\":\"Partido no encontrado\"}")
                                             .build();
    }
}
