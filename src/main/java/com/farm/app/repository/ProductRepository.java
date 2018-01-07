package com.farm.app.repository;

import com.farm.app.domain.Product;

import org.springframework.data.mongodb.repository.MongoRepository;


@SuppressWarnings("unused")
public interface ProductRepository extends MongoRepository<Product,String> {

}
