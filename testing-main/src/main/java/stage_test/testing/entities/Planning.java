package stage_test.testing.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Planning {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_pl;

    @ManyToOne
    @JoinColumn(name = "collaborateur_id")
    private Collaborateur collaborateur;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private Service_Dep serviceDep;

    @Temporal(TemporalType.DATE)
    private Date date;
}
