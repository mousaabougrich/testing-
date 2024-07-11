package stage_test.testing.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import stage_test.testing.entities.Service_Dep;

public interface ServiceRepository extends JpaRepository<Service_Dep, Integer> {
    Service_Dep findByNom(String nom);
    void deleteByNom(String nom);
}
