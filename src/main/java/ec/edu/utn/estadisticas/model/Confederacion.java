package ec.edu.utn.estadisticas.model;
import jakarta.persistence.*;

@Entity
@Table(name = "confederacion")
public class Confederacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idConfederacion;
    private String nombre;
    
    public Integer getIdConfederacion() { return idConfederacion; }
    public void setIdConfederacion(Integer idConfederacion) { this.idConfederacion = idConfederacion; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}
