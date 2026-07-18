package ec.edu.utn.estadisticas.audit;

import ec.edu.utn.estadisticas.filter.CurrentUserContext;
import ec.edu.utn.estadisticas.model.AuditLog;
import ec.edu.utn.estadisticas.model.User;
import ec.edu.utn.estadisticas.repository.AuditLogRepository;
import ec.edu.utn.estadisticas.repository.UserRepository;
import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Logger;

// Auditoría (RF24). Cada vez que una acción marcada con @Auditable termina
// bien, se guarda en la tabla auditoria: qué se hizo, cuándo, y quién lo hizo
// (si había un admin con sesión).
//
// Si la acción falla, solo se anota en el log de la consola, como ya estaba
// antes. No se intenta guardar en la BD para no complicar las cosas cuando
// algo sale mal a mitad de camino.
@Auditable
@Interceptor
public class AuditInterceptor {
    private static final Logger LOG = Logger.getLogger(AuditInterceptor.class.getName());

    @Inject
    private AuditLogRepository auditLogRepo;
    @Inject
    private UserRepository userRepo;
    @Inject
    private CurrentUserContext currentUserContext;

    @AroundInvoke
    public Object audit(InvocationContext context) throws Exception {
        String methodName = context.getMethod().getName();
        String className = context.getMethod().getDeclaringClass().getSimpleName();
        String params = Arrays.toString(context.getParameters());

        try {
            Object result = context.proceed();
            saveAuditLog(methodName, className, params);
            LOG.info(String.format("[AUDIT - %s] Action completed successfully: %s", new Date(), methodName));
            return result;
        } catch (Exception e) {
            LOG.severe(String.format("[AUDIT - %s] Error in action: %s, Reason: %s",
                new Date(), methodName, e.getMessage()));
            throw e;
        }
    }

    private void saveAuditLog(String methodName, String className, String params) {
        try {
            AuditLog log = new AuditLog();
            log.setActionType(methodName);
            log.setDateTime(new Date());
            log.setAffectedTable(className);
            log.setDescription("Parámetros: " + truncate(params));

            String email = currentUserContext.getEmail();
            if (email != null) {
                User user = userRepo.findByEmail(email);
                log.setUser(user);
            }

            auditLogRepo.save(log);
        } catch (Exception ex) {
            // Si esto falla, no debe arruinar la acción que sí funcionó bien.
            // Solo lo dejamos anotado en el log.
            LOG.warning("No se pudo guardar el registro de auditoría: " + ex.getMessage());
        }
    }

    private String truncate(String text) {
        if (text == null) return "";
        return text.length() > 400 ? text.substring(0, 400) + "..." : text;
    }
}
