package ec.edu.utn.estadisticas.repository;

import ec.edu.utn.estadisticas.model.AuditLog;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class AuditLogRepository {

    @PersistenceContext(unitName = "EstadisticasPU")
    private EntityManager em;

    @Transactional
    public AuditLog save(AuditLog log) {
        em.persist(log);
        return log;
    }

    public List<AuditLog> findAll() {
        return em.createQuery("SELECT a FROM AuditLog a ORDER BY a.dateTime DESC", AuditLog.class)
                 .getResultList();
    }
}
