package ec.edu.utn.estadisticas.repository;

import ec.edu.utn.estadisticas.model.Phase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@ApplicationScoped
public class PhaseRepository {
    @PersistenceContext(unitName = "EstadisticasPU")
    private EntityManager em;

    public List<Phase> findAll() {
        return em.createQuery("SELECT p FROM Phase p ORDER BY p.startDate ASC", Phase.class).getResultList();
    }

    public Phase findById(Integer id) {
        return em.find(Phase.class, id);
    }
}
