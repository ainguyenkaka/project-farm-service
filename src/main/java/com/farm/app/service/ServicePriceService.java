package com.farm.app.service;

import com.farm.app.domain.ServicePrice;
import com.farm.app.repository.ServicePriceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicePriceService {
    private final Logger log = LoggerFactory.getLogger(ServicePriceService.class);

    private final ServicePriceRepository servicePackageRepository;

    public ServicePriceService(ServicePriceRepository servicePackageRepository) {
        this.servicePackageRepository = servicePackageRepository;
    }

    public ServicePrice save(ServicePrice servicePackage) {
        log.debug("Request to save ServicePrice : {}", servicePackage);
        ServicePrice result = servicePackageRepository.save(servicePackage);
        return result;
    }


    public List<ServicePrice> findAll() {
        log.debug("Request to get all ServicePrices");
        List<ServicePrice> result = servicePackageRepository.findAll();

        return result;
    }

    public ServicePrice findOne(String id) {
        log.debug("Request to get ServicePrice : {}", id);
        ServicePrice servicePackage = servicePackageRepository.findOne(id);
        return servicePackage;
    }

    public void delete(String id) {
        log.debug("Request to delete ServicePrice : {}", id);
        servicePackageRepository.delete(id);
    }
}
