package stage_test.testing.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class PlanningDTO {
    private String collaborateurNom;
    private String collaborateurPrenom;
    private String serviceNom;
    private String secretaireNom;
    private String secretairePrenom;
    private Date date;
}
