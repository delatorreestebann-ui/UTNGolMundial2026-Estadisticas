package ec.edu.utn.estadisticas.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.mindrot.jbcrypt.BCrypt;

/**
 * no guarda contraseña en texto plno
 * Aqui se usa el hash generado aquí
 */
@ApplicationScoped
public class PasswordService {

    /** Genera el hash seguro de una contraseña en texto plano, para guardarlo en la BD. */
    public String hash(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    /** Verifica si una contraseña en texto plano corresponde al hash guardado. */
    public boolean verify(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
