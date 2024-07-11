package stage_test.testing.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stage_test.testing.dtos.SecretaireDTO;
import stage_test.testing.entities.Secretaire;
import stage_test.testing.entities.Service_Dep;
import stage_test.testing.repositories.SecretaireRepository;
import stage_test.testing.repositories.ServiceRepository;
import stage_test.testing.repositories.UtilisateurRepository;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SecretaireService {

    @Autowired
    private SecretaireRepository secretaireRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Transactional
    public SecretaireDTO addSecretaire(SecretaireDTO secretaireDTO, String serviceName) {
        if (utilisateurRepository.findByEmail(secretaireDTO.getEmail()) != null) {
            throw new RuntimeException("User with email " + secretaireDTO.getEmail() + " already exists.");
        }

        Service_Dep serviceDep = serviceRepository.findByNom(serviceName);
        if (serviceDep == null) {
            throw new RuntimeException("Service not found with name: " + serviceName);
        }

        Secretaire secretaire = new Secretaire();
        secretaire.setNom(secretaireDTO.getNom());
        secretaire.setPrenom(secretaireDTO.getPrenom());
        secretaire.setEmail(secretaireDTO.getEmail());
        secretaire.setPassword(secretaireDTO.getPassword());
        secretaire.setServiceDep(serviceDep);

        Secretaire savedSecretaire = secretaireRepository.save(secretaire);

        return new SecretaireDTO(savedSecretaire.getNom(), savedSecretaire.getPrenom(), savedSecretaire.getEmail(), savedSecretaire.getPassword(), serviceDep.getNom());
    }

    @Transactional
    public void deleteSecretaire(String nom, String prenom) {
        secretaireRepository.deleteByNomAndPrenom(nom, prenom);
    }

    @Transactional
    public SecretaireDTO updateSecretaire(String nom, String prenom, SecretaireDTO secretaireDTO) {
        Secretaire existingSecretaire = secretaireRepository.findByNomAndPrenom(nom, prenom);
        if (existingSecretaire != null) {
            Service_Dep serviceDep = serviceRepository.findByNom(secretaireDTO.getServiceName());
            if (serviceDep == null) {
                throw new RuntimeException("Service not found with name: " + secretaireDTO.getServiceName());
            }
            existingSecretaire.setNom(secretaireDTO.getNom());
            existingSecretaire.setPrenom(secretaireDTO.getPrenom());
            existingSecretaire.setEmail(secretaireDTO.getEmail());
            existingSecretaire.setPassword(secretaireDTO.getPassword());
            existingSecretaire.setServiceDep(serviceDep);

            Secretaire updatedSecretaire = secretaireRepository.save(existingSecretaire);

            return new SecretaireDTO(updatedSecretaire.getNom(), updatedSecretaire.getPrenom(), updatedSecretaire.getEmail(), updatedSecretaire.getPassword(), serviceDep.getNom());
        } else {
            throw new RuntimeException("Secretaire not found with nom: " + nom + " and prenom: " + prenom);
        }
    }

    // Nouvelle méthode pour récupérer tous les secrétaires
    public List<SecretaireDTO> getAllSecretaires() {
        return secretaireRepository.findAll().stream()
                .map(secretaire -> new SecretaireDTO(
                        secretaire.getNom(),
                        secretaire.getPrenom(),
                        secretaire.getEmail(),
                        secretaire.getPassword(),
                        secretaire.getServiceDep().getNom()))
                .collect(Collectors.toList());
    }
}
