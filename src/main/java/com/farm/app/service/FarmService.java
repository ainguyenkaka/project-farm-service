package com.farm.app.service;

import com.farm.app.domain.Farm;
import com.farm.app.repository.FarmRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FarmService {

    private final Logger log = LoggerFactory.getLogger(FarmService.class);

    private final FarmRepository farmRepository;

    public FarmService(FarmRepository farmRepository) {
        this.farmRepository = farmRepository;
    }

    public Farm save(Farm farm) {
        log.debug("Request to save Farm : {}", farm);
        Farm result = farmRepository.save(farm);
        return result;
    }

    public List<Farm> findAll() {
        log.debug("Request to get all Farms");
        List<Farm> result = farmRepository.findAll();

        return result;
    }


    public Farm findOne(String id) {
        log.debug("Request to get Farm : {}", id);
        Farm farm = farmRepository.findOne(id);
        return farm;
    }

    public void delete(String id) {
        log.debug("Request to delete Farm : {}", id);
        farmRepository.delete(id);
    }
}
