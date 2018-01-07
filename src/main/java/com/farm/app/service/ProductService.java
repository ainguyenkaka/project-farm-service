package com.farm.app.service;

import com.farm.app.domain.Product;
import com.farm.app.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final Logger log = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    public Product save(Product product) {
        log.debug("Request to save Product : {}", product);
        Product result = productRepository.save(product);
        return result;
    }

    public List<Product> findAll() {
        log.debug("Request to get all Products");
        List<Product> result = productRepository.findAll();

        return result;
    }

    public Product findOne(String id) {
        log.debug("Request to get Product : {}", id);
        Product product = productRepository.findOne(id);
        return product;
    }


    public void delete(String id) {
        log.debug("Request to delete Product : {}", id);
        productRepository.delete(id);
    }
}
