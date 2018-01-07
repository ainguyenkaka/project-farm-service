package com.farm.app.service;

import com.farm.app.domain.ServicePackage;
import com.farm.app.repository.ServicePackageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ServicePackageService {

    private final Logger log = LoggerFactory.getLogger(ServicePackageService.class);

    private final ServicePackageRepository servicePackageRepository;

    public ServicePackageService(ServicePackageRepository servicePackageRepository) {
        this.servicePackageRepository = servicePackageRepository;
    }


    public ServicePackage save(ServicePackage servicePackage) {
        log.debug("Request to save ServicePackage : {}", servicePackage);
        ServicePackage result = servicePackageRepository.save(servicePackage);
        return result;
    }


    public List<ServicePackage> findAll() {
        log.debug("Request to get all ServicePackages");
        List<ServicePackage> result = servicePackageRepository.findAll();

        return result;
    }

    public ServicePackage findOne(String id) {
        log.debug("Request to get ServicePackage : {}", id);
        ServicePackage servicePackage = servicePackageRepository.findOne(id);
        return servicePackage;
    }

    public void delete(String id) {
        log.debug("Request to delete ServicePackage : {}", id);
        servicePackageRepository.delete(id);
    }

    public ServicePackage findOneByIdAndUserId(String id, String userId) {
        log.debug("Request to get ServicePackage : {} and userId {}", id, userId);
        ServicePackage servicePackage = servicePackageRepository.findOneByIdAndUserId(id,userId);
        return servicePackage;
    }

    public List<ServicePackage> findAllByUserId(String userId) {
        log.debug("Request to get all ServicePackages with user: {}", userId);
        return servicePackageRepository.findAllByUserId(userId);
    }
}
