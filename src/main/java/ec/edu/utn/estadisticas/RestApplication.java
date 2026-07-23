package ec.edu.utn.estadisticas;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import java.util.Set;

@ApplicationPath("/api")
@OpenAPIDefinition(
    servers = {
        @Server(url = "/estadisticas-backend", description = "Servidor local WildFly")
    }
)




@SecurityScheme(
    name = "basicAuth",
    type = SecuritySchemeType.HTTP,
    scheme = "basic"
)
public class RestApplication extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        return Set.of(
            OpenApiResource.class,
            ec.edu.utn.estadisticas.resource.MatchResource.class,
            ec.edu.utn.estadisticas.resource.TeamResource.class,
            ec.edu.utn.estadisticas.resource.GroupResource.class,
            ec.edu.utn.estadisticas.resource.RoleResource.class,
            ec.edu.utn.estadisticas.resource.UserResource.class,
            ec.edu.utn.estadisticas.resource.LoginResource.class,
            ec.edu.utn.estadisticas.resource.LogoutResource.class,
            ec.edu.utn.estadisticas.resource.VenueResource.class,
            ec.edu.utn.estadisticas.resource.PhaseResource.class,
            ec.edu.utn.estadisticas.resource.AuditLogResource.class,
            ec.edu.utn.estadisticas.filter.AuthorizationFilter.class
        );
    }
}
