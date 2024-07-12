package stage_test.testing.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@DiscriminatorValue("sec")
@Data
public class Secretaire extends Utilisateur {
    @OneToOne
    @JoinColumn(name = "service_id")
    @JsonIgnore
    private Service_Dep serviceDep;
}
