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

// Este filtro decide quién puede hacer qué.
//
// - Para leer datos (GET), no pide nada, cualquiera puede entrar como invitado.
//   La única excepción es todo lo relacionado a /usuarios: ahí sí siempre
//   pide ser administrador, incluso solo para consultar.
// - login, logout y el registro de un usuario nuevo (POST /usuarios) tampoco
//   piden nada, porque en ese momento la persona todavía no tiene con qué
//   identificarse (recién se está registrando o iniciando sesión).
// - Para todo lo demás (crear o editar algo), sí exige email y contraseña
//   (Basic Auth) de un ADMINISTRADOR.
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
        String path = requestContext.getUriInfo().getPath(); // relativo a /api, ej: "usuarios/5"
        if (path.startsWith("/")) {
            path = path.substring(1); // por si el servidor lo entrega con "/" al inicio
        }
        String method = requestContext.getMethod();

        // Rutas siempre públicas, sin excepción
        if (path.equals("login") || path.equals("logout")) {
            return;
        }
        if (path.equals("usuarios") && "POST".equalsIgnoreCase(method)) {
            return; // registro público (RF01)
        }

        boolean isUserManagement = path.equals("usuarios") || path.startsWith("usuarios/");

        // Lecturas abiertas para invitados (RF26), salvo la gestión de usuarios
        if ("GET".equalsIgnoreCase(method) && !isUserManagement) {
            return;
        }

        // De aquí en adelante, se exige un ADMINISTRADOR autenticado
        String authHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Basic ")) {
            abort(requestContext, Response.Status.UNAUTHORIZED, "Se requiere autenticación (Basic Auth)");
            return;
        }

        String[] credentials = decode(authHeader); // credentials[0] = email, credentials[1] = password
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

        // Ya pasó todo. Guardamos quién es, para que después el AuditInterceptor
        // sepa a quién anotarle esta acción.
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
