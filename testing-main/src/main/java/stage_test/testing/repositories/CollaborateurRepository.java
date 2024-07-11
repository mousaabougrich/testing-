package stage_test.testing.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import stage_test.testing.entities.Collaborateur;
import stage_test.testing.entities.Service_Dep;

import java.util.List;

public interface CollaborateurRepository extends JpaRepository<Collaborateur, Long> {
    List<Collaborateur> findByServiceDep(Service_Dep serviceDep);
    Collaborateur findByNomAndPrenom(String nom, String prenom);

    @Transactional
    void deleteByNomAndPrenom(String nom, String prenom);
}
