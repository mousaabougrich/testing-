package stage_test.testing.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Planning {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_pl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collaborateur_id")
    @JsonManagedReference
    private Collaborateur collaborateur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id")
    @JsonBackReference
    private Service_Dep serviceDep;

    @Temporal(TemporalType.DATE)
    private Date date;

    private boolean isGuardDuty;
}
