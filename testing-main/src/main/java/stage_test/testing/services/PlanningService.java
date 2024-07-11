package stage_test.testing.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stage_test.testing.dtos.PlanningDTO;
import stage_test.testing.entities.Collaborateur;
import stage_test.testing.entities.Planning;
import stage_test.testing.entities.Service_Dep;
import stage_test.testing.repositories.CollaborateurRepository;
import stage_test.testing.repositories.PlanningRepository;
import stage_test.testing.repositories.ServiceRepository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlanningService {

    private static final Logger logger = LoggerFactory.getLogger(PlanningService.class);

    @Autowired
    private PlanningRepository planningRepository;

    @Autowired
    private CollaborateurRepository collaborateurRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    private static final int SATURDAY = Calendar.SATURDAY;
    private static final int SUNDAY = Calendar.SUNDAY;

    @Transactional
    public void updateWeekendPlannings() {
        List<Service_Dep> services = serviceRepository.findAll();
        logger.info("Found {} services", services.size());

        for (Service_Dep service : services) {
            List<Collaborateur> collaborateurs = collaborateurRepository.findByServiceDep(service);
            logger.info("Found {} collaborateurs for service {}", collaborateurs.size(), service.getNom());

            if (!collaborateurs.isEmpty()) {
                Calendar calendar = Calendar.getInstance();
                int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

                if (dayOfWeek == SATURDAY || dayOfWeek == SUNDAY) {
                    Planning lastWeekendPlanning = planningRepository.findTopByServiceDepOrderByDateDesc(service);
                    Collaborateur nextCollaborateur;

                    if (lastWeekendPlanning == null) {
                        nextCollaborateur = collaborateurs.get(0);
                    } else {
                        int lastCollaborateurIndex = collaborateurs.indexOf(lastWeekendPlanning.getCollaborateur());
                        int nextCollaborateurIndex = (lastCollaborateurIndex + 1) % collaborateurs.size();
                        nextCollaborateur = collaborateurs.get(nextCollaborateurIndex);
                    }

                    Planning newPlanning = new Planning();
                    newPlanning.setCollaborateur(nextCollaborateur);
                    newPlanning.setServiceDep(service);
                    newPlanning.setDate(new Date());
                    planningRepository.save(newPlanning);

                    logger.info("Assigned collaborateur {} to weekend planning for service {}",
                            nextCollaborateur.getNom(), service.getNom());
                } else {
                    logger.info("Today is not a weekend day, no planning created for service {}", service.getNom());
                }
            }
        }
    }

    public List<PlanningDTO> getWeekendPlannings() {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        if (dayOfWeek == SATURDAY || dayOfWeek == SUNDAY) {
            Date startDate = getStartOfCurrentWeekend();
            Date endDate = getEndOfCurrentWeekend();

            return serviceRepository.findAll().stream()
                    .flatMap(serviceDep -> planningRepository.findByServiceDepAndDateBetween(serviceDep, startDate, endDate).stream()
                            .map(planning -> new PlanningDTO(
                                    planning.getCollaborateur().getNom(),
                                    planning.getCollaborateur().getPrenom(),
                                    planning.getServiceDep().getNom(),
                                    planning.getServiceDep().getSecretaire().getNom(),
                                    planning.getServiceDep().getSecretaire().getPrenom(),
                                    planning.getDate())))
                    .collect(Collectors.toList());
        }

        logger.info("Today is not a weekend day, no plannings to show");
        return List.of();
    }

    private Date getStartOfCurrentWeekend() {
        Calendar calendar = Calendar.getInstance();
        while (calendar.get(Calendar.DAY_OF_WEEK) != SATURDAY) {
            calendar.add(Calendar.DATE, -1);
        }
        return calendar.getTime();
    }

    private Date getEndOfCurrentWeekend() {
        Calendar calendar = Calendar.getInstance();
        while (calendar.get(Calendar.DAY_OF_WEEK) != SUNDAY) {
            calendar.add(Calendar.DATE, 1);
        }
        return calendar.getTime();
    }
}
