package stage_test.testing.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Collaborateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_col;
    private String nom;
    private String prenom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id")
   @JsonIgnore
    private Service_Dep serviceDep;

    @OneToMany(mappedBy = "collaborateur")
    @JsonIgnore
    private List<Planning> plannings;
}
