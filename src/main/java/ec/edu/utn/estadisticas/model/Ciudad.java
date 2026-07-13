package ec.edu.utn.estadisticas.model;
import jakarta.persistence.*;

@Entity
@Table(name = "ciudad")
public class Ciudad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCiudad;
    private String nombre;
    
    @ManyToOne
    @JoinColumn(name = "idPais")
    private Pais pais;

    public Integer getIdCiudad() { return idCiudad; }
    public void setIdCiudad(Integer idCiudad) { this.idCiudad = idCiudad; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public Pais getPais() { return pais; }
    public void setPais(Pais pais) { this.pais = pais; }
}
