package stage_test.testing.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import stage_test.testing.entities.Service_Dep;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollaborateurDTO {
    private Long id_col;
    private String nom;
    private String prenom;
    private String serviceName;
}
