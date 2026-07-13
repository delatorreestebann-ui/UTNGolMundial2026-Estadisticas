package ec.edu.utn.estadisticas.repository;

import ec.edu.utn.estadisticas.model.Seleccion;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;

/**
 * Repositorio JPA para la entidad Seleccion.
 */
@ApplicationScoped
public class SeleccionRepository {

    @PersistenceContext(unitName = "EstadisticasPU")
    private EntityManager em;

    public List<Seleccion> findAll() {
        return em.createQuery(
            "SELECT s FROM Seleccion s ORDER BY s.nombre ASC", Seleccion.class)
            .getResultList();
    }

    public Seleccion findById(Integer id) {
        return em.find(Seleccion.class, id);
    }

    /** Actualiza los stats de una selección tras registrar un resultado. */
    @Transactional
    public Seleccion update(Seleccion seleccion) {
        return em.merge(seleccion);
    }
}
