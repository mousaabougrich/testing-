package stage_test.testing.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stage_test.testing.dtos.PlanningDTO;
import stage_test.testing.services.PlanningService;

import java.util.List;

@RestController
@RequestMapping("/plannings")
public class PlanningController {

    @Autowired
    private PlanningService planningService;

    @PutMapping("/update-weekend")
    public void updateWeekendPlannings() {
        planningService.updateWeekendPlannings();
    }

    @GetMapping("/weekend")
    public List<PlanningDTO> getWeekendPlannings() {
        return planningService.getWeekendPlannings();
    }
}
