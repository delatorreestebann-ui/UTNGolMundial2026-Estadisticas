package ec.edu.utn.estadisticas.repository;
import ec.edu.utn.estadisticas.model.Role;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@ApplicationScoped
public class RoleRepository {
    @PersistenceContext(unitName = "EstadisticasPU")
    private EntityManager em;

    public List<Role> findAll() {
        return em.createQuery("SELECT r FROM Role r ORDER BY r.idRole ASC", Role.class).getResultList();
    }

    public Role findById(Integer id) {
        return em.find(Role.class, id);
    }
}
