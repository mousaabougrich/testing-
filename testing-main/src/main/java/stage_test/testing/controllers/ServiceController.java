package stage_test.testing.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import stage_test.testing.entities.Service_Dep;
import stage_test.testing.services.ServiceService;

import java.util.List;

@RestController
@RequestMapping("/services")
public class ServiceController {

    @Autowired
    private ServiceService serviceService;

    @PostMapping("/add")
    public Service_Dep addService(@RequestBody Service_Dep service) {
        return serviceService.addService(service);
    }

    @DeleteMapping("/delete/{nom}")
    public void deleteService(@PathVariable String nom) {
        serviceService.deleteService(nom);
    }

    @PutMapping("/update/{nom}")
    public Service_Dep updateService(@PathVariable String nom, @RequestBody Service_Dep service) {
        return serviceService.updateService(nom, service);
    }

    @GetMapping("/all")
    public List<Service_Dep> getAllServices() {
        return serviceService.getAllServices();
    }
}
