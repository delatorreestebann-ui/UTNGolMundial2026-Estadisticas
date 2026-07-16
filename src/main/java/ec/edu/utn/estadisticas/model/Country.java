package ec.edu.utn.estadisticas.model;
import jakarta.persistence.*;

@Entity
@Table(name = "pais")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idpais")
    private Integer idCountry;

    @Column(name = "nombre")
    private String name;

    public Integer getIdCountry() { return idCountry; }
    public void setIdCountry(Integer idCountry) { this.idCountry = idCountry; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}