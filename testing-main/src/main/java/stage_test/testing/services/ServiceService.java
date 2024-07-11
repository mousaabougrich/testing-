package stage_test.testing.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stage_test.testing.entities.Service_Dep;
import stage_test.testing.repositories.ServiceRepository;

import java.util.List;

@Service
public class ServiceService {

    @Autowired
    private ServiceRepository serviceRepository;

    // Add a new service
    public Service_Dep addService(Service_Dep service) {
        return serviceRepository.save(service);
    }

    // Delete a service by name
    @Transactional
    public void deleteService(String nom) {
        serviceRepository.deleteByNom(nom);
    }

    // Modify an existing service by name
    @Transactional
    public Service_Dep updateService(String nom, Service_Dep service) {
        Service_Dep existingService = serviceRepository.findByNom(nom);
        if (existingService != null) {
            existingService.setNom(service.getNom());
            // Update other fields as necessary
            return serviceRepository.save(existingService);
        } else {
            throw new RuntimeException("Service not found with name: " + nom);
        }
    }

    // Display all services
    public List<Service_Dep> getAllServices() {
        return serviceRepository.findAll();
    }
}
