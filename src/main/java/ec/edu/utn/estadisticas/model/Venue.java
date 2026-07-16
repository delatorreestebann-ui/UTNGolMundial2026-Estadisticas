package ec.edu.utn.estadisticas.model;
import jakarta.persistence.*;

@Entity
@Table(name = "sede")
public class Venue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idsede")
    private Integer idVenue;

    @Column(name = "estadio")
    private String stadium;

    @Column(name = "capacidad")
    private Integer capacity;

    @ManyToOne
    @JoinColumn(name = "idciudad")
    private City city;

    public Integer getIdVenue() { return idVenue; }
    public void setIdVenue(Integer idVenue) { this.idVenue = idVenue; }
    public String getStadium() { return stadium; }
    public void setStadium(String stadium) { this.stadium = stadium; }
    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }
    public City getCity() { return city; }
    public void setCity(City city) { this.city = city; }
}