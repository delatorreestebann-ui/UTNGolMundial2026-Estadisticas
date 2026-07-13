package ec.edu.utn.estadisticas.repository;

import ec.edu.utn.estadisticas.model.Partido;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;

/**
 * Repositorio JPA para la entidad Partido.
 * Provee acceso a los 104 partidos del torneo.
 */
@ApplicationScoped
public class PartidoRepository {

    @PersistenceContext(unitName = "EstadisticasPU")
    private EntityManager em;

    /** Retorna todos los partidos ordenados por fecha. */
    public List<Partido> findAll() {
        return em.createQuery(
            "SELECT p FROM Partido p ORDER BY p.fechaHoraUtc ASC", Partido.class)
            .getResultList();
    }

    /** Retorna partidos filtrados por código de fase (ej: "GRUPOS", "OCTAVOS"). */
    public List<Partido> findByFase(String codigoFase) {
        return em.createQuery(
            "SELECT p FROM Partido p WHERE p.fase.codigo = :codigo ORDER BY p.fechaHoraUtc ASC",
            Partido.class)
            .setParameter("codigo", codigoFase)
            .getResultList();
    }

    /** Retorna partidos de un grupo específico. */
    public List<Partido> findByGrupo(Integer idGrupo) {
        return em.createQuery(
            "SELECT p FROM Partido p WHERE p.grupo.idGrupo = :idGrupo ORDER BY p.fechaHoraUtc ASC",
            Partido.class)
            .setParameter("idGrupo", idGrupo)
            .getResultList();
    }

    /** Busca un partido por su ID. */
    public Partido findById(Integer id) {
        return em.find(Partido.class, id);
    }

    /** Persiste un nuevo partido (para fases eliminatorias). */
    @Transactional
    public Partido save(Partido partido) {
        em.persist(partido);
        return partido;
    }

    /** Actualiza un partido existente. */
    @Transactional
    public Partido update(Partido partido) {
        return em.merge(partido);
    }
}
