package ec.edu.utn.estadisticas.filter;

import ec.edu.utn.estadisticas.model.User;
import ec.edu.utn.estadisticas.repository.UserRepository;
import ec.edu.utn.estadisticas.service.PasswordService;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import java.util.Base64;











@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthorizationFilter implements ContainerRequestFilter {

    private static final String ADMIN_ROLE = "ADMINISTRADOR";

    @Inject
    private UserRepository userRepo;
    @Inject
    private PasswordService passwordService;
    @Inject
    private CurrentUserContext currentUserContext;

    @Override
    public void filter(ContainerRequestContext requestContext) {
        String path = requestContext.getUriInfo().getPath(); 
        if (path.startsWith("/")) {
            path = path.substring(1); 
        }
        String method = requestContext.getMethod();

        
        if (path.equals("login") || path.equals("logout")) {
            return;
        }
        if (path.equals("usuarios") && "POST".equalsIgnoreCase(method)) {
            return; 
        }

        boolean isUserManagement = path.equals("usuarios") || path.startsWith("usuarios/");
        boolean isAuditLog = path.equals("auditoria") || path.startsWith("auditoria/");

        
        
        if ("GET".equalsIgnoreCase(method) && !isUserManagement && !isAuditLog) {
            return;
        }

        
        String authHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Basic ")) {
            abort(requestContext, Response.Status.UNAUTHORIZED, "Se requiere autenticación (Basic Auth)");
            return;
        }

        String[] credentials = decode(authHeader); 
        if (credentials == null) {
            abort(requestContext, Response.Status.UNAUTHORIZED, "Encabezado de autenticación inválido");
            return;
        }

        User user = userRepo.findByEmail(credentials[0]);
        if (user == null || !Boolean.TRUE.equals(user.getActive())
                || !passwordService.verify(credentials[1], user.getPasswordHash())) {
            abort(requestContext, Response.Status.UNAUTHORIZED, "Credenciales inválidas");
            return;
        }

        if (user.getRole() == null || !ADMIN_ROLE.equals(user.getRole().getName())) {
            abort(requestContext, Response.Status.FORBIDDEN, "No tiene permisos de administrador para esta acción");
            return;
        }

        
        
        currentUserContext.setEmail(user.getEmail());
    }

    private String[] decode(String authHeader) {
        try {
            String base64 = authHeader.substring("Basic ".length()).trim();
            String decoded = new String(Base64.getDecoder().decode(base64));
            int sep = decoded.indexOf(':');
            if (sep < 0) return null;
            return new String[]{decoded.substring(0, sep), decoded.substring(sep + 1)};
        } catch (Exception e) {
            return null;
        }
    }

    private void abort(ContainerRequestContext ctx, Response.Status status, String message) {
        ctx.abortWith(Response.status(status).entity("{\"error\":\"" + message + "\"}").build());
    }
}
