



package stage_test.testing.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import stage_test.testing.entities.Collaborateur;
import stage_test.testing.entities.Planning;
import stage_test.testing.entities.Service_Dep;

import java.util.Date;
import java.util.List;

public interface PlanningRepository extends JpaRepository<Planning, Integer> {
    List<Planning> findByIsGuardDutyTrueAndDate(Date date);

    @Query("SELECT p FROM Planning p WHERE p.serviceDep = :serviceDep AND p.isGuardDuty = true AND p.date < :date ORDER BY p.date DESC")
    List<Planning> findByServiceDepAndIsGuardDutyTrueAndDateBeforeOrderByDateDesc(Service_Dep serviceDep, Date date);

    @Query("SELECT p FROM Planning p WHERE p.collaborateur = :collaborateur AND p.isGuardDuty = true AND p.date > :date ORDER BY p.date DESC")
    List<Planning> findByCollaborateurAndIsGuardDutyTrueAndDateAfterOrderByDateDesc(Collaborateur collaborateur, Date date);

    List<Planning> findByDate(Date date); // Add this method
}