package stage_test.testing.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonBackReference
    private Service_Dep serviceDep;

    @OneToMany(mappedBy = "collaborateur", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Planning> plannings;

    @OneToMany(mappedBy = "collaborateur", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Disponibilite> disponibilites;
}
