package ec.edu.utn.estadisticas.dto;
import java.util.Date;

public class AuditLogDTO {
    public Integer idAuditLog;
    public String actionType;
    public Date dateTime;
    public String affectedTable;
    public String description;
    public String userEmail;
    public String userName;
}
