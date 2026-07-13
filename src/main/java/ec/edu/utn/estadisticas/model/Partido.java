package ec.edu.utn.estadisticas.model;
import jakarta.persistence.*;
import java.util.Date;
import java.math.BigDecimal;

@Entity
@Table(name = "partido")
public class Partido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPartido;
    
    private Integer numeroPartidoFifa;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraUtc;
    
    private String estado;
    private Integer golesLocal;
    private Integer golesVisitante;
    private BigDecimal cuotaEmpate;
    private BigDecimal cuotaVisitante;
    private BigDecimal cuotaLocal;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaResultadoRegistrado;
    
    @ManyToOne
    @JoinColumn(name = "idFase")
    private Fase fase;
    
    @ManyToOne
    @JoinColumn(name = "idSede")
    private Sede sede;
    
    @ManyToOne
    @JoinColumn(name = "idGrupo")
    private Grupo grupo;
    
    @ManyToOne
    @JoinColumn(name = "idSeleccionLocal")
    private Seleccion seleccionLocal;
    
    @ManyToOne
    @JoinColumn(name = "idSeleccionVisitante")
    private Seleccion seleccionVisitante;

    // Getters & Setters
    public Integer getIdPartido() { return idPartido; }
    public void setIdPartido(Integer idPartido) { this.idPartido = idPartido; }
    public Integer getNumeroPartidoFifa() { return numeroPartidoFifa; }
    public void setNumeroPartidoFifa(Integer numeroPartidoFifa) { this.numeroPartidoFifa = numeroPartidoFifa; }
    public Date getFechaHoraUtc() { return fechaHoraUtc; }
    public void setFechaHoraUtc(Date fechaHoraUtc) { this.fechaHoraUtc = fechaHoraUtc; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public Integer getGolesLocal() { return golesLocal; }
    public void setGolesLocal(Integer golesLocal) { this.golesLocal = golesLocal; }
    public Integer getGolesVisitante() { return golesVisitante; }
    public void setGolesVisitante(Integer golesVisitante) { this.golesVisitante = golesVisitante; }
    public BigDecimal getCuotaEmpate() { return cuotaEmpate; }
    public void setCuotaEmpate(BigDecimal cuotaEmpate) { this.cuotaEmpate = cuotaEmpate; }
    public BigDecimal getCuotaVisitante() { return cuotaVisitante; }
    public void setCuotaVisitante(BigDecimal cuotaVisitante) { this.cuotaVisitante = cuotaVisitante; }
    public BigDecimal getCuotaLocal() { return cuotaLocal; }
    public void setCuotaLocal(BigDecimal cuotaLocal) { this.cuotaLocal = cuotaLocal; }
    public Date getFechaResultadoRegistrado() { return fechaResultadoRegistrado; }
    public void setFechaResultadoRegistrado(Date fechaResultadoRegistrado) { this.fechaResultadoRegistrado = fechaResultadoRegistrado; }
    public Fase getFase() { return fase; }
    public void setFase(Fase fase) { this.fase = fase; }
    public Sede getSede() { return sede; }
    public void setSede(Sede sede) { this.sede = sede; }
    public Grupo getGrupo() { return grupo; }
    public void setGrupo(Grupo grupo) { this.grupo = grupo; }
    public Seleccion getSeleccionLocal() { return seleccionLocal; }
    public void setSeleccionLocal(Seleccion seleccionLocal) { this.seleccionLocal = seleccionLocal; }
    public Seleccion getSeleccionVisitante() { return seleccionVisitante; }
    public void setSeleccionVisitante(Seleccion seleccionVisitante) { this.seleccionVisitante = seleccionVisitante; }
}
