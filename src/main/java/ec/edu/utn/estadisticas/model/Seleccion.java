package ec.edu.utn.estadisticas.model;
import jakarta.persistence.*;

@Entity
@Table(name = "seleccion")
public class Seleccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSeleccion;
    
    private String nombre;
    private String codigoFifa;
    private Boolean esAnfitrion;
    private String clasificacion;
    
    // Estadisticas (pueden ser calculadas o almacenadas)
    private Integer partidosJugados = 0;
    private Integer puntos = 0;
    private Integer partidosGanados = 0;
    private Integer partidosEmpatados = 0;
    private Integer partidosPerdidos = 0;
    private Integer golesFavor = 0;
    private Integer golesContra = 0;
    
    @ManyToOne
    @JoinColumn(name = "idGrupo")
    private Grupo grupo;
    
    @ManyToOne
    @JoinColumn(name = "idConfederacion")
    private Confederacion confederacion;

    // Getters & Setters
    public Integer getIdSeleccion() { return idSeleccion; }
    public void setIdSeleccion(Integer idSeleccion) { this.idSeleccion = idSeleccion; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getCodigoFifa() { return codigoFifa; }
    public void setCodigoFifa(String codigoFifa) { this.codigoFifa = codigoFifa; }
    public Boolean getEsAnfitrion() { return esAnfitrion; }
    public void setEsAnfitrion(Boolean esAnfitrion) { this.esAnfitrion = esAnfitrion; }
    public String getClasificacion() { return clasificacion; }
    public void setClasificacion(String clasificacion) { this.clasificacion = clasificacion; }
    public Integer getPartidosJugados() { return partidosJugados; }
    public void setPartidosJugados(Integer partidosJugados) { this.partidosJugados = partidosJugados; }
    public Integer getPuntos() { return puntos; }
    public void setPuntos(Integer puntos) { this.puntos = puntos; }
    public Integer getPartidosGanados() { return partidosGanados; }
    public void setPartidosGanados(Integer partidosGanados) { this.partidosGanados = partidosGanados; }
    public Integer getPartidosEmpatados() { return partidosEmpatados; }
    public void setPartidosEmpatados(Integer partidosEmpatados) { this.partidosEmpatados = partidosEmpatados; }
    public Integer getPartidosPerdidos() { return partidosPerdidos; }
    public void setPartidosPerdidos(Integer partidosPerdidos) { this.partidosPerdidos = partidosPerdidos; }
    public Integer getGolesFavor() { return golesFavor; }
    public void setGolesFavor(Integer golesFavor) { this.golesFavor = golesFavor; }
    public Integer getGolesContra() { return golesContra; }
    public void setGolesContra(Integer golesContra) { this.golesContra = golesContra; }
    public Grupo getGrupo() { return grupo; }
    public void setGrupo(Grupo grupo) { this.grupo = grupo; }
    public Confederacion getConfederacion() { return confederacion; }
    public void setConfederacion(Confederacion confederacion) { this.confederacion = confederacion; }
}
