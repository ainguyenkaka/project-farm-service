package com.farm.app.web.rest.app;

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
@RequestMapping("/api/app")
public class AppServicePriceResource {

    private final Logger log = LoggerFactory.getLogger(AppServicePriceResource.class);

    private static final String ENTITY_NAME = "servicePrice";

    private final ServicePriceService servicePriceService;

    public AppServicePriceResource(ServicePriceService servicePriceService) {
        this.servicePriceService = servicePriceService;
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

    @GetMapping("/service-prices/top")
    @Timed
    public ResponseEntity<ServicePrice> getServicePrice() {
        log.debug("REST request to get ServicePrice top");
        ServicePrice servicePrice = servicePriceService.findAll().get(0);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(servicePrice));
    }
}
