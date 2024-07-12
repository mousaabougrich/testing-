package stage_test.testing.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    @Autowired
    private PlanningRepository planningRepository;

    @Autowired
    private ServiceRepository serviceDepRepository;

    @Autowired
    private CollaborateurRepository collaborateurRepository;

    public List<Planning> getGuardDutySchedule(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
            return planningRepository.findByIsGuardDutyTrueAndDate(date);
        }

        return null;
    }

    public void updateGuardDutySchedule() {
        List<Service_Dep> services = getAllServices();
        for (Service_Dep service : services) {
            List<Collaborateur> collaborateurs = service.getCollaborateurs();
            if (!collaborateurs.isEmpty()) {
                Collaborateur selectedCollaborateur = collaborateurs.get(0); // Or any logic to select a collaborator
                Planning planning = new Planning();
                planning.setCollaborateur(selectedCollaborateur);
                planning.setServiceDep(service);
                planning.setDate(getNextSaturdayOrSunday());
                planning.setGuardDuty(true);

                planningRepository.save(planning);
            }
        }
    }

    public Planning addGuardDuty(Date date, Collaborateur collaborateur, Service_Dep serviceDep) {
        Planning planning = new Planning();
        planning.setDate(date);
        planning.setCollaborateur(collaborateur);
        planning.setServiceDep(serviceDep);
        planning.setGuardDuty(true);
        return planningRepository.save(planning);
    }

    public void fillPlanningTable(Date startDate, Date endDate) {
        List<Service_Dep> services = getAllServices();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);

        while (!calendar.getTime().after(endDate)) {
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
                for (Service_Dep service : services) {
                    List<Collaborateur> collaborateurs = service.getCollaborateurs();
                    if (!collaborateurs.isEmpty()) {
                        Collaborateur nextCollaborateur = getNextCollaborateur(service, calendar.getTime());

                        Planning planning = new Planning();
                        planning.setDate(calendar.getTime());
                        planning.setCollaborateur(nextCollaborateur);
                        planning.setServiceDep(service);
                        planning.setGuardDuty(true);
                        planningRepository.save(planning);

                        // Assign the same collaborator for Sunday if today is Saturday
                        if (dayOfWeek == Calendar.SATURDAY) {
                            calendar.add(Calendar.DATE, 1); // Move to Sunday
                            Planning sundayPlanning = new Planning();
                            sundayPlanning.setDate(calendar.getTime());
                            sundayPlanning.setCollaborateur(nextCollaborateur);
                            sundayPlanning.setServiceDep(service);
                            sundayPlanning.setGuardDuty(true);
                            planningRepository.save(sundayPlanning);
                        }
                    }
                }
            }
            calendar.add(Calendar.DATE, 1);
        }
    }

    private Collaborateur getNextCollaborateur(Service_Dep service, Date date) {
        List<Collaborateur> collaborateurs = service.getCollaborateurs();
        List<Collaborateur> availableCollaborateurs = collaborateurs.stream()
                .filter(c -> !hasRecentGuardDuty(c, date))
                .collect(Collectors.toList());

        if (availableCollaborateurs.isEmpty()) {
            return collaborateurs.get(0);
        }

        return availableCollaborateurs.get(0);
    }

    private boolean hasRecentGuardDuty(Collaborateur collaborateur, Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.WEEK_OF_YEAR, -1);
        Date oneWeekBefore = calendar.getTime();

        List<Planning> recentGuardDuties = planningRepository.findByCollaborateurAndIsGuardDutyTrueAndDateAfterOrderByDateDesc(collaborateur, oneWeekBefore);
        return !recentGuardDuties.isEmpty();
    }

    private Date getNextSaturdayOrSunday() {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek != Calendar.SATURDAY) {
            calendar.add(Calendar.DAY_OF_WEEK, Calendar.SATURDAY - dayOfWeek);
        }
        return calendar.getTime();
    }

    private List<Service_Dep> getAllServices() {
        return serviceDepRepository.findAll();
    }

    private List<Collaborateur> getAllCollaborateurs() {
        return collaborateurRepository.findAll();




    }
    public List<Planning> getPlanningByDate(Date date) {
        return planningRepository.findByDate(date);}

}
