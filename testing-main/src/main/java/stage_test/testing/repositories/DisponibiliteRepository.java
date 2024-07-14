package stage_test.testing.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import stage_test.testing.entities.Disponibilite;

public interface DisponibiliteRepository extends JpaRepository<Disponibilite, Long> {
}
