package ec.edu.utn.estadisticas.filter;

import jakarta.enterprise.context.RequestScoped;




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
