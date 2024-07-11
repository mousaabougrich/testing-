package stage_test.testing.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import stage_test.testing.dtos.CollaborateurDTO;
import stage_test.testing.entities.Collaborateur;
import stage_test.testing.entities.Service_Dep;
import stage_test.testing.services.CollaborateurService;

import java.util.List;

@RestController
@RequestMapping("/collaborateurs")
public class CollaborateurController {

    @Autowired
    private CollaborateurService collaborateurService;

    @PostMapping("/add")
    public CollaborateurDTO addCollaborateur(@RequestBody Collaborateur collaborateur, @RequestParam String serviceName) {
        return collaborateurService.addCollaborateur(collaborateur, serviceName);
    }

    @DeleteMapping("/delete")
    public void deleteCollaborateur(@RequestParam String nom, @RequestParam String prenom) {
        collaborateurService.deleteCollaborateur(nom, prenom);
    }

    @PutMapping("/update")
    public CollaborateurDTO updateCollaborateur(@RequestParam String nom, @RequestParam String prenom, @RequestBody Collaborateur collaborateur, @RequestParam String serviceName) {
        return collaborateurService.updateCollaborateur(nom, prenom, collaborateur, serviceName);
    }

    @GetMapping("/service/{serviceId}")
    public List<CollaborateurDTO> getCollaborateursByServiceDep(@PathVariable("serviceId") Service_Dep serviceDep) {
        return collaborateurService.getCollaborateursByServiceDep(serviceDep);
    }
}
