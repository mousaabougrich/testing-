package stage_test.testing.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import stage_test.testing.entities.Collaborateur;
import stage_test.testing.entities.Planning;
import stage_test.testing.entities.Service_Dep;
import stage_test.testing.services.PlanningService;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/planning")
public class PlanningController {

    @Autowired
    private PlanningService planningService;

    @GetMapping("/guard-schedule")
    public List<Planning> getGuardDutySchedule(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        return planningService.getGuardDutySchedule(date);
    }

    @PostMapping("/update-guard-schedule")
    public void updateGuardDutySchedule() {
        planningService.updateGuardDutySchedule();
    }

    @PostMapping("/add-guard-duty")
    public Planning addGuardDuty(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
                                 @RequestParam("collaborateurId") Long collaborateurId,
                                 @RequestParam("serviceDepId") Integer serviceDepId) {
        Collaborateur collaborateur = new Collaborateur();
        collaborateur.setId_col(collaborateurId);

        Service_Dep serviceDep = new Service_Dep();
        serviceDep.setId_s(serviceDepId);

        return planningService.addGuardDuty(date, collaborateur, serviceDep);
    }

    @PostMapping("/fill-planning")
    public void fillPlanningTable(@RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                  @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        planningService.fillPlanningTable(startDate, endDate);
    }

    @GetMapping("/by-date")
    public List<Planning> getPlanningByDate(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        return planningService.getPlanningByDate(date);
    }
}
