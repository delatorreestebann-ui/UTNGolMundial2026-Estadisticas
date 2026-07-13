package ec.edu.utn.estadisticas.model;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "fase")
public class Fase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idFase;
    
    private String codigo;
    private String nombre;
    
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    
    @Temporal(TemporalType.DATE)
    private Date fechaFin;
    
    // Getters and Setters
    public Integer getIdFase() { return idFase; }
    public void setIdFase(Integer idFase) { this.idFase = idFase; }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public Date getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(Date fechaInicio) { this.fechaInicio = fechaInicio; }
    public Date getFechaFin() { return fechaFin; }
    public void setFechaFin(Date fechaFin) { this.fechaFin = fechaFin; }
}
