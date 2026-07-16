package ec.edu.utn.estadisticas.model;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "fase")
public class Phase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idfase")
    private Integer idPhase;

    @Column(name = "codigo")
    private String code;

    @Column(name = "nombre")
    private String name;

    @Temporal(TemporalType.DATE)
    @Column(name = "fechainicio")
    private Date startDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "fechafin")
    private Date endDate;

    public Integer getIdPhase() { return idPhase; }
    public void setIdPhase(Integer idPhase) { this.idPhase = idPhase; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Date getStartDate() { return startDate; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }
    public Date getEndDate() { return endDate; }
    public void setEndDate(Date endDate) { this.endDate = endDate; }
}