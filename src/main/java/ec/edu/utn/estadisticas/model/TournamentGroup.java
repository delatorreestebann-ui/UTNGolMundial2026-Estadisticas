package ec.edu.utn.estadisticas.model;
import jakarta.persistence.*;

@Entity
@Table(name = "grupo")
public class TournamentGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idgrupo")
    private Integer idGroup;

    @Column(name = "codigo")
    private String code;

    @Column(name = "nombre")
    private String name;

    public Integer getIdGroup() { return idGroup; }
    public void setIdGroup(Integer idGroup) { this.idGroup = idGroup; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}