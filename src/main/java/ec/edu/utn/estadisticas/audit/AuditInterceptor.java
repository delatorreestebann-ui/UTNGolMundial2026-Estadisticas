package ec.edu.utn.estadisticas.audit;

import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import java.util.logging.Logger;
import java.util.Arrays;
import java.time.LocalDateTime;

/**
 * Audit logging implementation (RF24).
 * Logs date, time, executed method (action) and parameters.
 */
@Auditable
@Interceptor
public class AuditInterceptor {
    private static final Logger LOG = Logger.getLogger(AuditInterceptor.class.getName());

    @AroundInvoke
    public Object audit(InvocationContext context) throws Exception {
        String methodName = context.getMethod().getName();
        String params = Arrays.toString(context.getParameters());
        LOG.info(String.format("[AUDIT - %s] Action executed: %s, Parameters: %s",
            LocalDateTime.now(), methodName, params));
        try {
            Object result = context.proceed();
            LOG.info(String.format("[AUDIT - %s] Action completed successfully: %s",
                LocalDateTime.now(), methodName));
            return result;
        } catch (Exception e) {
            LOG.severe(String.format("[AUDIT - %s] Error in action: %s, Reason: %s",
                LocalDateTime.now(), methodName, e.getMessage()));
            throw e;
        }
    }
}