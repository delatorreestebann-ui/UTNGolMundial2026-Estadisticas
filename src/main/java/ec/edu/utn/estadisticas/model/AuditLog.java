package ec.edu.utn.estadisticas.model;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "auditoria")
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idauditoria")
    private Integer idAuditLog;

    @Column(name = "tipoaccion")
    private String actionType;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fechahora")
    private Date dateTime;

    @Column(name = "tablaafectada")
    private String affectedTable;

    @Column(name = "descripcion")
    private String description;

    @ManyToOne
    @JoinColumn(name = "idusuario")
    private User user;

    public Integer getIdAuditLog() { return idAuditLog; }
    public void setIdAuditLog(Integer idAuditLog) { this.idAuditLog = idAuditLog; }
    public String getActionType() { return actionType; }
    public void setActionType(String actionType) { this.actionType = actionType; }
    public Date getDateTime() { return dateTime; }
    public void setDateTime(Date dateTime) { this.dateTime = dateTime; }
    public String getAffectedTable() { return affectedTable; }
    public void setAffectedTable(String affectedTable) { this.affectedTable = affectedTable; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
