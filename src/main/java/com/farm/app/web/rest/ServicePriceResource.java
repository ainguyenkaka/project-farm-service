package com.farm.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.farm.app.domain.ServicePrice;
import com.farm.app.service.ServicePriceService;
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
public class ServicePriceResource {

    private final Logger log = LoggerFactory.getLogger(ServicePriceResource.class);

    private static final String ENTITY_NAME = "servicePrice";

    private final ServicePriceService servicePriceService;

    public ServicePriceResource(ServicePriceService servicePriceService) {
        this.servicePriceService = servicePriceService;
    }


    @PostMapping("/service-prices")
    @Timed
    public ResponseEntity<ServicePrice> createServicePrice(@Valid @RequestBody ServicePrice servicePrice) throws URISyntaxException {
        log.debug("REST request to save ServicePrice : {}", servicePrice);
        if (servicePrice.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new servicePrice cannot already have an ID")).body(null);
        }
        ServicePrice result = servicePriceService.save(servicePrice);
        return ResponseEntity.created(new URI("/api/service-prices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/service-prices")
    @Timed
    public ResponseEntity<ServicePrice> updateServicePrice(@Valid @RequestBody ServicePrice servicePrice) throws URISyntaxException {
        log.debug("REST request to update ServicePrice : {}", servicePrice);
        if (servicePrice.getId() == null) {
            return createServicePrice(servicePrice);
        }
        ServicePrice result = servicePriceService.save(servicePrice);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, servicePrice.getId().toString()))
            .body(result);
    }

    @GetMapping("/service-prices")
    @Timed
    public List<ServicePrice> getAllServicePrices() {
        log.debug("REST request to get all ServicePrices");
        return servicePriceService.findAll();
    }


    @GetMapping("/service-prices/{id}")
    @Timed
    public ResponseEntity<ServicePrice> getServicePrice(@PathVariable String id) {
        log.debug("REST request to get ServicePrice : {}", id);
        ServicePrice servicePrice = servicePriceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(servicePrice));
    }

    @DeleteMapping("/service-prices/{id}")
    @Timed
    public ResponseEntity<Void> deleteServicePrice(@PathVariable String id) {
        log.debug("REST request to delete ServicePrice : {}", id);
        servicePriceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
