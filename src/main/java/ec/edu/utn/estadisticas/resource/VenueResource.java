package ec.edu.utn.estadisticas.resource;

import ec.edu.utn.estadisticas.dto.VenueDTO;
import ec.edu.utn.estadisticas.model.Venue;
import ec.edu.utn.estadisticas.repository.VenueRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;


@Path("/sedes")
@Produces(MediaType.APPLICATION_JSON)
public class VenueResource {

    @Inject
    private VenueRepository repo;

    @GET
    public Response getVenues() {
        List<VenueDTO> venues = repo.findAll().stream().map(this::toDTO).collect(Collectors.toList());
        return Response.ok(venues).build();
    }

    @GET
    @Path("/{id}")
    public Response getVenue(@PathParam("id") Integer id) {
        Venue v = repo.findById(id);
        return v != null ? Response.ok(toDTO(v)).build()
                         : Response.status(Response.Status.NOT_FOUND)
                                   .entity("{\"error\":\"Sede no encontrada\"}")
                                   .build();
    }

    private VenueDTO toDTO(Venue v) {
        VenueDTO dto = new VenueDTO();
        dto.idVenue = v.getIdVenue();
        dto.stadium = v.getStadium();
        dto.capacity = v.getCapacity();
        if (v.getCity() != null) {
            dto.city = v.getCity().getName();
            if (v.getCity().getCountry() != null) {
                dto.country = v.getCity().getCountry().getName();
            }
        }
        return dto;
    }
}
