package ec.edu.utn.estadisticas.dto;

/**
 * dto para registrar o actualizar un usuario.
 * solo el admin, al actualizar, puede cambiar idrole y active.
 */
public class UserInputDTO {
    public String name;
    public String email;
    public String username;
    public String password;
    public Integer idRole;
    public Boolean active;
}
