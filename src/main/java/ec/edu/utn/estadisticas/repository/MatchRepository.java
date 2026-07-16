package ec.edu.utn.estadisticas.repository;

import ec.edu.utn.estadisticas.model.Match;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class MatchRepository {

    @PersistenceContext(unitName = "EstadisticasPU")
    private EntityManager em;

    public List<Match> findAll() {
        return em.createQuery(
            "SELECT m FROM Match m ORDER BY m.matchDateTimeUtc ASC", Match.class)
            .getResultList();
    }

    public List<Match> findByPhase(String phaseCode) {
        return em.createQuery(
            "SELECT m FROM Match m WHERE m.phase.code = :code ORDER BY m.matchDateTimeUtc ASC",
            Match.class)
            .setParameter("code", phaseCode)
            .getResultList();
    }

    public List<Match> findByGroup(Integer idGroup) {
        return em.createQuery(
            "SELECT m FROM Match m WHERE m.group.idGroup = :idGroup ORDER BY m.matchDateTimeUtc ASC",
            Match.class)
            .setParameter("idGroup", idGroup)
            .getResultList();
    }

    public Match findById(Integer id) {
        return em.find(Match.class, id);
    }

    @Transactional
    public Match save(Match match) {
        em.persist(match);
        return match;
    }

    @Transactional
    public Match update(Match match) {
        return em.merge(match);
    }
}