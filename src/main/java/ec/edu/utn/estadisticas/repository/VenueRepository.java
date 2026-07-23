package ec.edu.utn.estadisticas.repository;

import ec.edu.utn.estadisticas.model.Venue;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@ApplicationScoped
public class VenueRepository {
    @PersistenceContext(unitName = "EstadisticasPU")
    private EntityManager em;

    public List<Venue> findAll() {
        return em.createQuery("SELECT v FROM Venue v ORDER BY v.stadium ASC", Venue.class).getResultList();
    }

    public Venue findById(Integer id) {
        return em.find(Venue.class, id);
    }
}
