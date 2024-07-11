package stage_test.testing.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import stage_test.testing.entities.Planning;
import stage_test.testing.entities.Service_Dep;

import java.util.Date;
import java.util.List;

public interface PlanningRepository extends JpaRepository<Planning, Integer> {
    Planning findTopByServiceDepOrderByDateDesc(Service_Dep serviceDep);

    List<Planning> findByServiceDepAndDateBetween(Service_Dep serviceDep, Date startDate, Date endDate);
}
