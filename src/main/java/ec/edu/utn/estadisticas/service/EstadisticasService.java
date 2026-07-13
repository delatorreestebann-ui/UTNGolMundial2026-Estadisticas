package ec.edu.utn.estadisticas.service;

import ec.edu.utn.estadisticas.dto.*;
import ec.edu.utn.estadisticas.model.*;
import ec.edu.utn.estadisticas.repository.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import ec.edu.utn.estadisticas.audit.Auditable;

/**
 * Servicio central del Backend de Estadísticas.
 * Implementa la lógica de negocio para:
 *   - Consulta de partidos, grupos, selecciones y calendario.
 *   - Tabla de posiciones por grupo (RF05, RF06).
 *   - Estadísticas por selección (RF07).
 *   - Registro de resultado oficial con recálculo automático de posiciones (RF06, RF11).
 *   - Creación de partidos de fases eliminatorias (RF10).
 */
@ApplicationScoped
public class EstadisticasService {

    @Inject
    private PartidoRepository partidoRepo;
    @Inject
    private GrupoRepository grupoRepo;
    @Inject
    private SeleccionRepository seleccionRepo;
    @Inject
    private NotificacionService notificacionService;

    // ----------------------------------------------------------------
    //  PARTIDOS — Consulta
    // ----------------------------------------------------------------

    /** Retorna todos los partidos del torneo (104 cuando estén todos cargados). */
    public List<PartidoDTO> getPartidos() {
        return partidoRepo.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    /** Retorna partidos filtrados por código de fase (ej: "GRUPOS", "OCTAVOS"). */
    public List<PartidoDTO> getPartidosByFase(String codigoFase) {
        return partidoRepo.findByFase(codigoFase).stream().map(this::toDTO).collect(Collectors.toList());
    }

    /** Retorna el detalle de un partido específico. */
    public PartidoDTO getPartido(Integer id) {
        Partido p = partidoRepo.findById(id);
        return p != null ? toDTO(p) : null;
    }

    /** Retorna todos los partidos de un grupo (para el calendario del frontend). */
    public List<PartidoDTO> getPartidosByGrupo(Integer idGrupo) {
        return partidoRepo.findByGrupo(idGrupo).stream().map(this::toDTO).collect(Collectors.toList());
    }

    // ----------------------------------------------------------------
    //  PARTIDOS — Registro de resultado oficial (RF06, RF11, RF12)
    // ----------------------------------------------------------------

    /**
     * Registra el resultado oficial de un partido y actualiza automáticamente
     * las estadísticas y tabla de posiciones de ambas selecciones involucradas.
     * Luego notifica al Servicio UTNGolCoin para liquidación de predicciones (RF12).
     *
     * @param idPartido ID del partido a actualizar.
     * @param dto       DTO con los goles del local y visitante.
     * @return PartidoDTO actualizado, o null si no se encontró el partido.
     */
    @Transactional
    @Auditable
    public PartidoDTO registrarResultado(Integer idPartido, ResultadoDTO dto) {
        Partido partido = partidoRepo.findById(idPartido);
        if (partido == null) return null;

        // 1. Actualizar el marcador y estado del partido
        partido.setGolesLocal(dto.golesLocal);
        partido.setGolesVisitante(dto.golesVisitante);
        partido.setEstado("FINALIZADO");
        partido.setFechaResultadoRegistrado(new Date());
        partidoRepo.update(partido);

        // 2. Recalcular estadísticas de ambas selecciones (solo si es fase de grupos)
        if (partido.getGrupo() != null) {
            actualizarEstadisticasSeleccion(partido.getSeleccionLocal(), dto.golesLocal, dto.golesVisitante);
            actualizarEstadisticasSeleccion(partido.getSeleccionVisitante(), dto.golesVisitante, dto.golesLocal);
        }

        // 3. Notificar a UTNGolCoin para liquidar predicciones (RF12)
        //    La notificación es asíncrona: si falla, el resultado ya fue guardado.
        notificacionService.notificarResultado(idPartido);

        return toDTO(partido);
    }

    /**
     * Actualiza los contadores estadísticos de una selección según el resultado obtenido.
     * golesF = goles que hizo esta selección; golesC = goles que le hicieron.
     */
    private void actualizarEstadisticasSeleccion(Seleccion s, int golesF, int golesC) {
        if (s == null) return;
        s.setPartidosJugados(safe(s.getPartidosJugados()) + 1);
        s.setGolesFavor(safe(s.getGolesFavor()) + golesF);
        s.setGolesContra(safe(s.getGolesContra()) + golesC);

        if (golesF > golesC) {
            // Victoria
            s.setPartidosGanados(safe(s.getPartidosGanados()) + 1);
            s.setPuntos(safe(s.getPuntos()) + 3);
        } else if (golesF == golesC) {
            // Empate
            s.setPartidosEmpatados(safe(s.getPartidosEmpatados()) + 1);
            s.setPuntos(safe(s.getPuntos()) + 1);
        } else {
            // Derrota
            s.setPartidosPerdidos(safe(s.getPartidosPerdidos()) + 1);
        }
        seleccionRepo.update(s);
    }

    private int safe(Integer v) { return v != null ? v : 0; }

    // ----------------------------------------------------------------
    //  PARTIDOS — CRUD administrativo (RF10)
    // ----------------------------------------------------------------

    /**
     * Crea un nuevo partido (usado para agregar partidos de fases eliminatorias).
     */
    @Transactional
    @Auditable
    public PartidoDTO crearPartido(PartidoInputDTO dto) {
        Partido p = new Partido();
        aplicarInputDTO(p, dto);
        return toDTO(partidoRepo.save(p));
    }

    /**
     * Actualiza los datos de un partido existente (sede, hora, selecciones, cuotas).
     */
    @Transactional
    public PartidoDTO actualizarPartido(Integer idPartido, PartidoInputDTO dto) {
        Partido p = partidoRepo.findById(idPartido);
        if (p == null) return null;
        aplicarInputDTO(p, dto);
        return toDTO(partidoRepo.update(p));
    }

    private void aplicarInputDTO(Partido p, PartidoInputDTO dto) {
        p.setNumeroPartidoFifa(dto.numeroPartidoFifa);
        p.setEstado(dto.estado != null ? dto.estado : "PROGRAMADO");
        if (dto.cuotaLocal != null) p.setCuotaLocal(BigDecimal.valueOf(dto.cuotaLocal));
        if (dto.cuotaEmpate != null) p.setCuotaEmpate(BigDecimal.valueOf(dto.cuotaEmpate));
        if (dto.cuotaVisitante != null) p.setCuotaVisitante(BigDecimal.valueOf(dto.cuotaVisitante));
        if (dto.fechaHoraUtc != null) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
                p.setFechaHoraUtc(sdf.parse(dto.fechaHoraUtc));
            } catch (Exception ignored) {}
        }
        // Relaciones: carga lazy por ID
        if (dto.idFase != null) {
            Fase f = new Fase(); f.setIdFase(dto.idFase); p.setFase(f);
        }
        if (dto.idSede != null) {
            Sede s = new Sede(); s.setIdSede(dto.idSede); p.setSede(s);
        }
        if (dto.idGrupo != null) {
            Grupo g = new Grupo(); g.setIdGrupo(dto.idGrupo); p.setGrupo(g);
        }
        if (dto.idSeleccionLocal != null) {
            Seleccion sl = new Seleccion(); sl.setIdSeleccion(dto.idSeleccionLocal); p.setSeleccionLocal(sl);
        }
        if (dto.idSeleccionVisitante != null) {
            Seleccion sv = new Seleccion(); sv.setIdSeleccion(dto.idSeleccionVisitante); p.setSeleccionVisitante(sv);
        }
    }

    // ----------------------------------------------------------------
    //  GRUPOS y POSICIONES (RF05, RF06)
    // ----------------------------------------------------------------

    public List<GrupoDTO> getGrupos() {
        return grupoRepo.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    /**
     * Retorna la tabla de posiciones de un grupo, ordenada por:
     * Puntos desc → Diferencia de goles desc → Goles a favor desc.
     */
    public GrupoDTO getGrupoPosiciones(Integer id) {
        Grupo g = grupoRepo.findById(id);
        if (g == null) return null;
        GrupoDTO dto = toDTO(g);

        List<Seleccion> selecciones = grupoRepo.findSeleccionesByGrupo(id);
        List<PosicionDTO> posiciones = selecciones.stream()
            .map(this::toPosicionDTO)
            .sorted(Comparator.comparingInt((PosicionDTO p) -> p.puntos)
                .thenComparingInt(p -> p.diferenciaGoles)
                .thenComparingInt(p -> p.golesFavor)
                .reversed())
            .collect(Collectors.toList());

        dto.posiciones = posiciones;
        return dto;
    }

    // ----------------------------------------------------------------
    //  SELECCIONES (RF07)
    // ----------------------------------------------------------------

    /** Retorna todas las selecciones con sus estadísticas completas. */
    public List<SeleccionDTO> getSelecciones() {
        return seleccionRepo.findAll().stream().map(this::toSeleccionDTO).collect(Collectors.toList());
    }

    /** Retorna el detalle completo de una selección por ID. */
    public SeleccionDTO getSeleccion(Integer id) {
        Seleccion s = seleccionRepo.findById(id);
        return s != null ? toSeleccionDTO(s) : null;
    }

    // ----------------------------------------------------------------
    //  Mappers (Entidad → DTO)
    // ----------------------------------------------------------------

    private PartidoDTO toDTO(Partido p) {
        PartidoDTO dto = new PartidoDTO();
        dto.idPartido = p.getIdPartido();
        dto.numeroPartidoFifa = p.getNumeroPartidoFifa();
        dto.fechaHoraUtc = p.getFechaHoraUtc();
        dto.estado = p.getEstado();
        dto.seleccionLocal = p.getSeleccionLocal() != null ? p.getSeleccionLocal().getNombre() : null;
        dto.seleccionVisitante = p.getSeleccionVisitante() != null ? p.getSeleccionVisitante().getNombre() : null;
        dto.golesLocal = p.getGolesLocal();
        dto.golesVisitante = p.getGolesVisitante();
        dto.estadio = p.getSede() != null ? p.getSede().getEstadio() : null;
        dto.grupo = p.getGrupo() != null ? p.getGrupo().getNombre() : null;
        dto.fase = p.getFase() != null ? p.getFase().getNombre() : null;
        return dto;
    }

    private GrupoDTO toDTO(Grupo g) {
        GrupoDTO dto = new GrupoDTO();
        dto.idGrupo = g.getIdGrupo();
        dto.codigo = g.getCodigo();
        dto.nombre = g.getNombre();
        return dto;
    }

    private PosicionDTO toPosicionDTO(Seleccion s) {
        PosicionDTO dto = new PosicionDTO();
        dto.idSeleccion = s.getIdSeleccion();
        dto.nombre = s.getNombre();
        dto.codigoFifa = s.getCodigoFifa();
        dto.partidosJugados = safe(s.getPartidosJugados());
        dto.puntos = safe(s.getPuntos());
        dto.victorias = safe(s.getPartidosGanados());
        dto.empates = safe(s.getPartidosEmpatados());
        dto.derrotas = safe(s.getPartidosPerdidos());
        dto.golesFavor = safe(s.getGolesFavor());
        dto.golesContra = safe(s.getGolesContra());
        dto.diferenciaGoles = dto.golesFavor - dto.golesContra;
        return dto;
    }

    private SeleccionDTO toSeleccionDTO(Seleccion s) {
        SeleccionDTO dto = new SeleccionDTO();
        dto.idSeleccion = s.getIdSeleccion();
        dto.nombre = s.getNombre();
        dto.codigoFifa = s.getCodigoFifa();
        dto.esAnfitrion = s.getEsAnfitrion();
        dto.clasificacion = s.getClasificacion();
        dto.grupo = s.getGrupo() != null ? s.getGrupo().getNombre() : null;
        dto.confederacion = s.getConfederacion() != null ? s.getConfederacion().getNombre() : null;
        dto.partidosJugados = safe(s.getPartidosJugados());
        dto.puntos = safe(s.getPuntos());
        dto.victorias = safe(s.getPartidosGanados());
        dto.empates = safe(s.getPartidosEmpatados());
        dto.derrotas = safe(s.getPartidosPerdidos());
        dto.golesFavor = safe(s.getGolesFavor());
        dto.golesContra = safe(s.getGolesContra());
        dto.diferenciaGoles = dto.golesFavor - dto.golesContra;
        return dto;
    }
}
