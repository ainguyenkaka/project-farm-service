package com.farm.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.farm.app.domain.ServicePackage;
import com.farm.app.service.ServicePackageService;
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
public class ServicePackageResource {

    private final Logger log = LoggerFactory.getLogger(ServicePackageResource.class);

    private static final String ENTITY_NAME = "servicePackage";

    private final ServicePackageService servicePackageService;

    public ServicePackageResource(ServicePackageService servicePackageService) {
        this.servicePackageService = servicePackageService;
    }


    @PostMapping("/service-packages")
    @Timed
    public ResponseEntity<ServicePackage> createServicePackage(@Valid @RequestBody ServicePackage servicePackage) throws URISyntaxException {
        log.debug("REST request to save ServicePackage : {}", servicePackage);
        if (servicePackage.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new servicePackage cannot already have an ID")).body(null);
        }
        ServicePackage result = servicePackageService.save(servicePackage);
        return ResponseEntity.created(new URI("/api/service-packages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/service-packages")
    @Timed
    public ResponseEntity<ServicePackage> updateServicePackage(@Valid @RequestBody ServicePackage servicePackage) throws URISyntaxException {
        log.debug("REST request to update ServicePackage : {}", servicePackage);
        if (servicePackage.getId() == null) {
            return createServicePackage(servicePackage);
        }
        ServicePackage result = servicePackageService.save(servicePackage);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, servicePackage.getId().toString()))
            .body(result);
    }

    @GetMapping("/service-packages")
    @Timed
    public List<ServicePackage> getAllServicePackages() {
        log.debug("REST request to get all ServicePackages");
        return servicePackageService.findAll();
    }


    @GetMapping("/service-packages/{id}")
    @Timed
    public ResponseEntity<ServicePackage> getServicePackage(@PathVariable String id) {
        log.debug("REST request to get ServicePackage : {}", id);
        ServicePackage servicePackage = servicePackageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(servicePackage));
    }

    @DeleteMapping("/service-packages/{id}")
    @Timed
    public ResponseEntity<Void> deleteServicePackage(@PathVariable String id) {
        log.debug("REST request to delete ServicePackage : {}", id);
        servicePackageService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
