package ec.edu.utn.estadisticas.filter;

import jakarta.enterprise.context.RequestScoped;

// Guarda quién inició sesión durante esta petición
// El AuthorizationFilter lo llena cuando revisa las credenciales,
// y el AuditInterceptor lo usa después para saber a quién anotar.
@RequestScoped
public class CurrentUserContext {
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
