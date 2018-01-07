package com.farm.app.web.rest.app;

import com.codahale.metrics.annotation.Timed;
import com.farm.app.domain.Product;
import com.farm.app.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/app")
public class AppProductResource {
    private final Logger log = LoggerFactory.getLogger(AppProductResource.class);

    private static final String ENTITY_NAME = "product";

    private final ProductService productService;

    public AppProductResource(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    @Timed
    public List<Product> getAllProducts() {
        log.debug("REST request to get all Products");
        return productService.findAll();
    }
}
