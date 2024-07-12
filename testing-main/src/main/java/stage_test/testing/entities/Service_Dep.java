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
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "nom"))
public class Service_Dep {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_s;

    @Column(unique = true, nullable = false)
    private String nom;

    @OneToOne(mappedBy = "serviceDep")
    @JsonIgnore
    private Secretaire secretaire;

    @OneToMany(mappedBy = "serviceDep")
    @JsonIgnore
    private List<Collaborateur> collaborateurs;

    @OneToMany(mappedBy = "serviceDep")
    @JsonIgnore
    private List<Planning> plannings;
}
