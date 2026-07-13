package ec.edu.utn.estadisticas.dto;

/**
 * DTO para crear o actualizar un partido (usado por el panel administrativo).
 */
public class PartidoInputDTO {
    public Integer numeroPartidoFifa;
    public String fechaHoraUtc;   // ISO-8601: "2026-06-14T18:00:00Z"
    public String estado;         // PROGRAMADO | EN_JUEGO | FINALIZADO
    public Integer idFase;
    public Integer idSede;
    public Integer idGrupo;       // null para eliminatorias
    public Integer idSeleccionLocal;
    public Integer idSeleccionVisitante;
    public Double cuotaLocal;
    public Double cuotaEmpate;
    public Double cuotaVisitante;
}
