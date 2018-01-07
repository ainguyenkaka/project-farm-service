package com.farm.app.web.rest.app;

import com.codahale.metrics.annotation.Timed;
import com.farm.app.domain.Location;
import com.farm.app.service.LocationService;
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
public class AppLocationResource {

    private final Logger log = LoggerFactory.getLogger(AppLocationResource.class);

    private static final String ENTITY_NAME = "location";

    private final LocationService locationService;

    public AppLocationResource(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/locations")
    @Timed
    public List<Location> getAllLocations() {
        log.debug("REST request to get all Locations");
        return locationService.findAll();
    }

    @GetMapping("/locations/{id}")
    @Timed
    public ResponseEntity<Location> getLocation(@PathVariable String id) {
        log.debug("REST request to get Location : {}", id);
        Location location = locationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(location));
    }

    @GetMapping("/locations/top")
    @Timed
    public ResponseEntity<Location> getLocation() {
        log.debug("REST request to get Location top");
        Location location = locationService.findAll().get(0);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(location));
    }
}
