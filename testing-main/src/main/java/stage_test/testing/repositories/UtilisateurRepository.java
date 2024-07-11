package stage_test.testing.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import stage_test.testing.entities.Utilisateur;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    Utilisateur findByNomAndPrenom(String nom, String prenom);
    Utilisateur findByEmail(String email);  // Add this line
}
