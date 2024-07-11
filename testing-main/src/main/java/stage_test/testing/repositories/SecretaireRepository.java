package stage_test.testing.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import stage_test.testing.entities.Secretaire;

public interface SecretaireRepository extends JpaRepository<Secretaire, Long> {
    Secretaire findByNomAndPrenom(String nom, String prenom);

    @Transactional
    void deleteByNomAndPrenom(String nom, String prenom);
}
