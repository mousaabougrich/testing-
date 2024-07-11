package stage_test.testing.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stage_test.testing.dtos.CollaborateurDTO;
import stage_test.testing.entities.Collaborateur;
import stage_test.testing.entities.Service_Dep;
import stage_test.testing.repositories.CollaborateurRepository;
import stage_test.testing.repositories.ServiceRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CollaborateurService {

    @Autowired
    private CollaborateurRepository collaborateurRepository;

    @Autowired
    private ServiceRepository serviceRepository;


    public CollaborateurDTO addCollaborateur(Collaborateur collaborateur, String serviceName) {
        Service_Dep serviceDep = serviceRepository.findByNom(serviceName);
        if (serviceDep == null) {
            throw new RuntimeException("Service not found with name: " + serviceName);
        }
        collaborateur.setServiceDep(serviceDep);
        Collaborateur savedCollaborateur = collaborateurRepository.save(collaborateur);
        return new CollaborateurDTO(savedCollaborateur.getId_col(), savedCollaborateur.getNom(), savedCollaborateur.getPrenom(), serviceDep.getNom());
    }


    @Transactional
    public void deleteCollaborateur(String nom, String prenom) {
        collaborateurRepository.deleteByNomAndPrenom(nom, prenom);
    }


    @Transactional
    public CollaborateurDTO updateCollaborateur(String nom, String prenom, Collaborateur collaborateur, String serviceName) {
        Collaborateur existingCollaborateur = collaborateurRepository.findByNomAndPrenom(nom, prenom);
        if (existingCollaborateur != null) {
            Service_Dep serviceDep = serviceRepository.findByNom(serviceName);
            if (serviceDep == null) {
                throw new RuntimeException("Service not found with name: " + serviceName);
            }
            existingCollaborateur.setNom(collaborateur.getNom());
            existingCollaborateur.setPrenom(collaborateur.getPrenom());
            existingCollaborateur.setServiceDep(serviceDep);
            Collaborateur updatedCollaborateur = collaborateurRepository.save(existingCollaborateur);
            return new CollaborateurDTO(updatedCollaborateur.getId_col(), updatedCollaborateur.getNom(), updatedCollaborateur.getPrenom(), serviceDep.getNom());
        } else {
            throw new RuntimeException("Collaborateur not found with nom: " + nom + " and prenom: " + prenom);
        }
    }


    public List<CollaborateurDTO> getCollaborateursByServiceDep(Service_Dep serviceDep) {
        List<Collaborateur> collaborateurs = collaborateurRepository.findByServiceDep(serviceDep);
        return collaborateurs.stream()
                .map(collaborateur -> new CollaborateurDTO(collaborateur.getId_col(), collaborateur.getNom(), collaborateur.getPrenom(), serviceDep.getNom()))
                .collect(Collectors.toList());
    }
}
