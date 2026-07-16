package ec.edu.utn.estadisticas.repository;

import ec.edu.utn.estadisticas.model.Team;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class TeamRepository {

    @PersistenceContext(unitName = "EstadisticasPU")
    private EntityManager em;

    public List<Team> findAll() {
        return em.createQuery(
            "SELECT t FROM Team t ORDER BY t.name ASC", Team.class)
            .getResultList();
    }

    public Team findById(Integer id) {
        return em.find(Team.class, id);
    }

    @Transactional
    public Team update(Team team) {
        return em.merge(team);
    }
}