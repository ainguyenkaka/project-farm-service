package com.farm.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.farm.app.domain.CustomerInfo;
import com.farm.app.service.CustomerInfoService;
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
public class CustomerInfoResource {

    private final Logger log = LoggerFactory.getLogger(CustomerInfoResource.class);

    private static final String ENTITY_NAME = "customerInfo";

    private final CustomerInfoService customerInfoService;

    public CustomerInfoResource(CustomerInfoService customerInfoService) {
        this.customerInfoService = customerInfoService;
    }

    @PostMapping("/customer-infos")
    @Timed
    public ResponseEntity<CustomerInfo> createCustomerInfo(@Valid @RequestBody CustomerInfo customerInfo) throws URISyntaxException {
        log.debug("REST request to save CustomerInfo : {}", customerInfo);
        if (customerInfo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new customerInfo cannot already have an ID")).body(null);
        }
        CustomerInfo result = customerInfoService.save(customerInfo);
        return ResponseEntity.created(new URI("/api/app/customer-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/customer-infos")
    @Timed
    public ResponseEntity<CustomerInfo> updateCustomerInfo(@Valid @RequestBody CustomerInfo customerInfo) throws URISyntaxException {
        log.debug("REST request to update CustomerInfo : {}", customerInfo);
        if (customerInfo.getId() == null) {
            return createCustomerInfo(customerInfo);
        }
        CustomerInfo result = customerInfoService.save(customerInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, customerInfo.getId().toString()))
            .body(result);
    }

    @GetMapping("/customer-infos")
    @Timed
    public List<CustomerInfo> getAllCustomerInfos() {
        log.debug("REST request to get all CustomerInfos");
        return customerInfoService.findAll();
    }

    @GetMapping("/customer-infos/{id}")
    @Timed
    public ResponseEntity<CustomerInfo> getCustomerInfo(@PathVariable String id) {
        log.debug("REST request to get CustomerInfo : {}", id);
        CustomerInfo customerInfo = customerInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(customerInfo));
    }

    @DeleteMapping("/customer-infos/{id}")
    @Timed
    public ResponseEntity<Void> deleteCustomerInfo(@PathVariable String id) {
        log.debug("REST request to delete CustomerInfo : {}", id);
        customerInfoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
