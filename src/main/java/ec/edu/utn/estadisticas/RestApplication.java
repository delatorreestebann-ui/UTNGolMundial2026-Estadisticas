package ec.edu.utn.estadisticas;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import java.util.Set;

@ApplicationPath("/api")
public class RestApplication extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        return Set.of(
            OpenApiResource.class,
            ec.edu.utn.estadisticas.resource.PartidoResource.class,
            ec.edu.utn.estadisticas.resource.SeleccionResource.class,
            ec.edu.utn.estadisticas.resource.GrupoResource.class
        );
    }
}
