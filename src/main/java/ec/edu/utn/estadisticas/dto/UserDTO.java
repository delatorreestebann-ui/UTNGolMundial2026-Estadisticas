package ec.edu.utn.estadisticas.dto;
import java.util.Date;

/** dto de salida para el usuario no se expone en el api la contraseña */
public class UserDTO {
    public Integer idUser;
    public String name;
    public String email;
    public String username;
    public Boolean active;
    public Date registeredAt;
    public Date lastAccess;
    public String role;
    public Integer idRole;
}
