package com.farm.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.farm.app.domain.Farm;
import com.farm.app.service.FarmService;
import com.farm.app.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class FarmResource {

    private final Logger log = LoggerFactory.getLogger(FarmResource.class);

    private static final String ENTITY_NAME = "farm";

    private final FarmService farmService;

    public FarmResource(FarmService farmService) {
        this.farmService = farmService;
    }


    @PostMapping("/farms")
    @Timed
    public ResponseEntity<Farm> createFarm(@Valid @RequestBody Farm farm) throws URISyntaxException {
        log.debug("REST request to save Farm : {}", farm);
        if (farm.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new farm cannot already have an ID")).body(null);
        }
        Farm result = farmService.save(farm);
        return ResponseEntity.created(new URI("/api/farms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }


    @PutMapping("/farms")
    @Timed
    public ResponseEntity<Farm> updateFarm(@Valid @RequestBody Farm farm) throws URISyntaxException {
        log.debug("REST request to update Farm : {}", farm);
        if (farm.getId() == null) {
            return createFarm(farm);
        }
        Farm result = farmService.save(farm);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, farm.getId().toString()))
            .body(result);
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


    @DeleteMapping("/farms/{id}")
    @Timed
    public ResponseEntity<Void> deleteFarm(@PathVariable String id) {
        log.debug("REST request to delete Farm : {}", id);
        farmService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
