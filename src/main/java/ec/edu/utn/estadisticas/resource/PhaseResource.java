package ec.edu.utn.estadisticas.resource;

import ec.edu.utn.estadisticas.dto.PhaseDTO;
import ec.edu.utn.estadisticas.model.Phase;
import ec.edu.utn.estadisticas.repository.PhaseRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;


@Path("/fases")
@Produces(MediaType.APPLICATION_JSON)
public class PhaseResource {

    @Inject
    private PhaseRepository repo;

    @GET
    public Response getPhases() {
        List<PhaseDTO> phases = repo.findAll().stream().map(this::toDTO).collect(Collectors.toList());
        return Response.ok(phases).build();
    }

    @GET
    @Path("/{id}")
    public Response getPhase(@PathParam("id") Integer id) {
        Phase p = repo.findById(id);
        return p != null ? Response.ok(toDTO(p)).build()
                         : Response.status(Response.Status.NOT_FOUND)
                                   .entity("{\"error\":\"Fase no encontrada\"}")
                                   .build();
    }

    private PhaseDTO toDTO(Phase p) {
        PhaseDTO dto = new PhaseDTO();
        dto.idPhase = p.getIdPhase();
        dto.code = p.getCode();
        dto.name = p.getName();
        dto.startDate = p.getStartDate();
        dto.endDate = p.getEndDate();
        return dto;
    }
}
