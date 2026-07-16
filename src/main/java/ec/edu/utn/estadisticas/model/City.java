package ec.edu.utn.estadisticas.model;
import jakarta.persistence.*;

@Entity
@Table(name = "ciudad")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idciudad")
    private Integer idCity;

    @Column(name = "nombre")
    private String name;

    @ManyToOne
    @JoinColumn(name = "idpais")
    private Country country;

    public Integer getIdCity() { return idCity; }
    public void setIdCity(Integer idCity) { this.idCity = idCity; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Country getCountry() { return country; }
    public void setCountry(Country country) { this.country = country; }
}