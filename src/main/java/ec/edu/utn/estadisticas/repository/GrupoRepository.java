package ec.edu.utn.estadisticas.repository;
import ec.edu.utn.estadisticas.model.Grupo;
import ec.edu.utn.estadisticas.model.Seleccion;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@ApplicationScoped
public class GrupoRepository {
    @PersistenceContext(unitName = "EstadisticasPU")
    private EntityManager em;

    public List<Grupo> findAll() {
        return em.createQuery("SELECT g FROM Grupo g ORDER BY g.codigo ASC", Grupo.class).getResultList();
    }
    
    public Grupo findById(Integer id) {
        return em.find(Grupo.class, id);
    }
    
    public List<Seleccion> findSeleccionesByGrupo(Integer idGrupo) {
        return em.createQuery("SELECT s FROM Seleccion s WHERE s.grupo.idGrupo = :idGrupo", Seleccion.class)
                 .setParameter("idGrupo", idGrupo)
                 .getResultList();
    }
}
