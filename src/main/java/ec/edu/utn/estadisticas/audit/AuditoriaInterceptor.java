package ec.edu.utn.estadisticas.audit;

import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import java.util.logging.Logger;
import java.util.Arrays;
import java.time.LocalDateTime;

/**
 * Implementación del registro de auditoría (RF24).
 * Registra fecha, hora, método ejecutado (acción) y parámetros.
 */
@Auditable
@Interceptor
public class AuditoriaInterceptor {
    private static final Logger LOG = Logger.getLogger(AuditoriaInterceptor.class.getName());

    @AroundInvoke
    public Object auditar(InvocationContext context) throws Exception {
        String methodName = context.getMethod().getName();
        String params = Arrays.toString(context.getParameters());
        
        LOG.info(String.format("[AUDITORIA - %s] Acción ejecutada: %s, Parámetros: %s", 
                 LocalDateTime.now(), methodName, params));
        
        try {
            Object result = context.proceed();
            LOG.info(String.format("[AUDITORIA - %s] Acción completada exitosamente: %s", 
                     LocalDateTime.now(), methodName));
            return result;
        } catch (Exception e) {
            LOG.severe(String.format("[AUDITORIA - %s] Error en acción: %s, Motivo: %s", 
                       LocalDateTime.now(), methodName, e.getMessage()));
            throw e;
        }
    }
}
