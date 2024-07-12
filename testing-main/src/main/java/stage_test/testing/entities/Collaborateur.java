package stage_test.testing.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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

    @OneToMany(mappedBy = "collaborateur")
    @JsonBackReference
    private List<Planning> plannings;
}
