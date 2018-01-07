package com.farm.app.web.rest.app;

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
import java.util.Optional;

@RestController
@RequestMapping("/api/app")
public class AppCustomerInfoResource {

    private final Logger log = LoggerFactory.getLogger(AppCustomerInfoResource.class);

    private static final String ENTITY_NAME = "customerInfo";

    private final CustomerInfoService customerInfoService;

    public AppCustomerInfoResource(CustomerInfoService customerInfoService) {
        this.customerInfoService = customerInfoService;
    }

    @GetMapping("/customer-infos/{id}")
    @Timed
    public ResponseEntity<CustomerInfo> getCustomerInfo(@PathVariable String id, @RequestParam String userId) {
        log.debug("REST request to get CustomerInfo : {}", id);
        if (userId == null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "useridexists", "Must have userId param")).body(null);
        }
        CustomerInfo customerInfo = customerInfoService.findOneByIdAndUserId(id,userId);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(customerInfo));
    }

    @GetMapping("/customer-infos/top")
    @Timed
    public ResponseEntity<CustomerInfo> getUserCustomerInfo(@RequestParam String userId) {
        log.debug("REST request to get CustomerInfo with userId: {}", userId);
        if (userId == null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "useridexists", "Must have userId param")).body(null);
        }
        CustomerInfo customerInfo = customerInfoService.findAllByUserId(userId).get(0);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(customerInfo));
    }


    @PostMapping("/customer-infos")
    @Timed
    public ResponseEntity<CustomerInfo> createCustomerInfo(@Valid @RequestBody CustomerInfo customerInfo, @RequestParam String userId) throws URISyntaxException {
        log.debug("REST request to save CustomerInfo : {}", customerInfo);
        if (customerInfo.getId() != null || userId == null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new customerInfo cannot already have an ID and must have userId param")).body(null);
        }
        customerInfo.setUserId(userId);
        CustomerInfo result = customerInfoService.save(customerInfo);
        return ResponseEntity.created(new URI("/api/app/customer-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }


    @PutMapping("/customer-infos")
    @Timed
    public ResponseEntity<CustomerInfo> updateCustomerInfo(@Valid @RequestBody CustomerInfo customerInfo,@RequestParam String userId) throws URISyntaxException {
        log.debug("REST request to update CustomerInfo : {}", customerInfo);
        if (customerInfo.getId() == null || userId == null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A updated customerInfo must exist and must have userId param")).body(null);
        }
        CustomerInfo result = customerInfoService.save(customerInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, customerInfo.getId().toString()))
            .body(result);
    }
}
