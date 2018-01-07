package com.farm.app.repository;

import com.farm.app.domain.Farm;

import org.springframework.data.mongodb.repository.MongoRepository;

@SuppressWarnings("unused")
public interface FarmRepository extends MongoRepository<Farm,String> {


}
