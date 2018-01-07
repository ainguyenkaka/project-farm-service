package com.farm.app.web.rest.app;

import com.codahale.metrics.annotation.Timed;
import com.farm.app.domain.Farm;
import com.farm.app.service.FarmService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/app")
public class AppFarmResource {

    private final Logger log = LoggerFactory.getLogger(AppFarmResource.class);

    private final FarmService farmService;

    public AppFarmResource(FarmService farmService) {
        this.farmService = farmService;
    }

    @GetMapping("/farms")
    @Timed
    public List<Farm> getAllFarms() {
        log.debug("REST request to get all Farms");
        return farmService.findAll();
    }

    @GetMapping("/farms/{id}")
    @Timed
    public ResponseEntity<Farm> getFarm(@PathVariable String id) {
        log.debug("REST request to get Farm : {}", id);
        Farm farm = farmService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(farm));
    }

    @GetMapping("/farms/top")
    @Timed
    public ResponseEntity<Farm> getFarm() {
        log.debug("REST request to get first Farm");
        Farm farm = farmService.findAll().get(0);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(farm));
    }
}
