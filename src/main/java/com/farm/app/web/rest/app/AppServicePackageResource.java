package com.farm.app.web.rest.app;

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
import java.util.Optional;

@RestController
@RequestMapping("/api/app")
public class AppServicePackageResource {

    private final Logger log = LoggerFactory.getLogger(AppServicePackageResource.class);

    private static final String ENTITY_NAME = "servicePackage";

    private final ServicePackageService servicePackageService;

    public AppServicePackageResource(ServicePackageService servicePackageService) {
        this.servicePackageService = servicePackageService;
    }

    @PostMapping("/service-packages")
    @Timed
    public ResponseEntity<ServicePackage> createServicePackage(@Valid @RequestBody ServicePackage servicePackage, @RequestParam String userId) throws URISyntaxException {
        log.debug("REST request to save ServicePackage : {}", servicePackage);
        if (servicePackage.getId() != null || userId == null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new servicePackage cannot already have an ID ad Must have userId param")).body(null);
        }
        servicePackage.setUserId(userId);
        ServicePackage result = servicePackageService.save(servicePackage);
        return ResponseEntity.created(new URI("/api/app/service-packages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }



    @GetMapping("/service-packages/{id}")
    @Timed
    public ResponseEntity<ServicePackage> getServicePackage(@PathVariable String id,@RequestParam String userId) {
        log.debug("REST request to get ServicePackage : {}", id);
        if (userId == null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "useridexists", "Must have userId param")).body(null);
        }
        ServicePackage servicePackage = servicePackageService.findOneByIdAndUserId(id,userId);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(servicePackage));
    }

    @GetMapping("/service-packages/top")
    @Timed
    public ResponseEntity<ServicePackage> getUserServicePackage(@RequestParam String userId) {
        log.debug("REST request to get ServicePackage with user: {}", userId);
        if (userId == null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "useridexists", "Must have userId param")).body(null);
        }
        ServicePackage servicePackage = servicePackageService.findAllByUserId(userId).get(0);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(servicePackage));
    }


    @PutMapping("/service-packages")
    @Timed
    public ResponseEntity<ServicePackage> updateServicePackage(@Valid @RequestBody ServicePackage servicePackage,@RequestParam String userId) throws URISyntaxException {
        log.debug("REST request to update ServicePackage : {}", servicePackage);
        if (servicePackage.getId() == null || userId == null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A updated servicePackage must exist ad Must have userId param")).body(null);
        }
        ServicePackage result = servicePackageService.save(servicePackage);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, servicePackage.getId().toString()))
            .body(result);
    }
}
