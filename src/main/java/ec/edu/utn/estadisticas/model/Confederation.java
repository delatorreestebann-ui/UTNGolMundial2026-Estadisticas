package ec.edu.utn.estadisticas.model;
import jakarta.persistence.*;

@Entity
@Table(name = "confederacion")
public class Confederation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idconfederacion")
    private Integer idConfederation;

    @Column(name = "nombre")
    private String name;

    public Integer getIdConfederation() { return idConfederation; }
    public void setIdConfederation(Integer idConfederation) { this.idConfederation = idConfederation; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}