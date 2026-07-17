package ec.edu.utn.estadisticas.model;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "usuario")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idusuario")
    private Integer idUser;

    @Column(name = "nombre")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "username")
    private String username;

    /** Never store the plain password — only the hash (RNF04). */
    @Column(name = "passwordhash")
    private String passwordHash;

    @Column(name = "estado")
    private Boolean active = Boolean.TRUE;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecharegistro")
    private Date registeredAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ultimoacceso")
    private Date lastAccess;

    @ManyToOne
    @JoinColumn(name = "idrol")
    private Role role;

    public Integer getIdUser() { return idUser; }
    public void setIdUser(Integer idUser) { this.idUser = idUser; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
    public Date getRegisteredAt() { return registeredAt; }
    public void setRegisteredAt(Date registeredAt) { this.registeredAt = registeredAt; }
    public Date getLastAccess() { return lastAccess; }
    public void setLastAccess(Date lastAccess) { this.lastAccess = lastAccess; }
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
}
