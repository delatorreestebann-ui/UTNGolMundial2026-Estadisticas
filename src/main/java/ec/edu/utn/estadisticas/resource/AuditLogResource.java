package ec.edu.utn.estadisticas.resource;

import ec.edu.utn.estadisticas.dto.AuditLogDTO;
import ec.edu.utn.estadisticas.model.AuditLog;
import ec.edu.utn.estadisticas.repository.AuditLogRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;


@Path("/auditoria")
@Produces(MediaType.APPLICATION_JSON)
public class AuditLogResource {

    @Inject
    private AuditLogRepository repo;

    @GET
    @SecurityRequirement(name = "basicAuth")
    public Response getAuditLogs() {
        List<AuditLogDTO> logs = repo.findAll().stream().map(this::toDTO).collect(Collectors.toList());
        return Response.ok(logs).build();
    }

    private AuditLogDTO toDTO(AuditLog log) {
        AuditLogDTO dto = new AuditLogDTO();
        dto.idAuditLog = log.getIdAuditLog();
        dto.actionType = log.getActionType();
        dto.dateTime = log.getDateTime();
        dto.affectedTable = log.getAffectedTable();
        dto.description = log.getDescription();
        if (log.getUser() != null) {
            dto.userEmail = log.getUser().getEmail();
            dto.userName = log.getUser().getName();
        }
        return dto;
    }
}
