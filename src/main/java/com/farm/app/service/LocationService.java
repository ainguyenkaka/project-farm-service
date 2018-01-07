package com.farm.app.service;

import com.farm.app.domain.Location;
import com.farm.app.repository.LocationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {
    private final Logger log = LoggerFactory.getLogger(LocationService.class);

    private final LocationRepository servicePackageRepository;

    public LocationService(LocationRepository servicePackageRepository) {
        this.servicePackageRepository = servicePackageRepository;
    }

    public Location save(Location servicePackage) {
        log.debug("Request to save Location : {}", servicePackage);
        Location result = servicePackageRepository.save(servicePackage);
        return result;
    }


    public List<Location> findAll() {
        log.debug("Request to get all Locations");
        List<Location> result = servicePackageRepository.findAll();

        return result;
    }

    public Location findOne(String id) {
        log.debug("Request to get Location : {}", id);
        Location servicePackage = servicePackageRepository.findOne(id);
        return servicePackage;
    }

    public void delete(String id) {
        log.debug("Request to delete Location : {}", id);
        servicePackageRepository.delete(id);
    }
}
