package ec.edu.utn.estadisticas.repository;
import ec.edu.utn.estadisticas.model.TournamentGroup;
import ec.edu.utn.estadisticas.model.Team;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@ApplicationScoped
public class GroupRepository {
    @PersistenceContext(unitName = "EstadisticasPU")
    private EntityManager em;

    public List<TournamentGroup> findAll() {
        return em.createQuery("SELECT g FROM TournamentGroup g ORDER BY g.code ASC", TournamentGroup.class).getResultList();
    }

    public TournamentGroup findById(Integer id) {
        return em.find(TournamentGroup.class, id);
    }

    public List<Team> findTeamsByGroup(Integer idGroup) {
        return em.createQuery("SELECT t FROM Team t WHERE t.group.idGroup = :idGroup", Team.class)
                 .setParameter("idGroup", idGroup)
                 .getResultList();
    }
}