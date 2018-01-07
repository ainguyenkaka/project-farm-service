package com.farm.app.repository;

import com.farm.app.domain.ServicePackage;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


@SuppressWarnings("unused")
public interface ServicePackageRepository extends MongoRepository<ServicePackage,String> {

    ServicePackage findOneByIdAndUserId(String id,String userId);

    List<ServicePackage> findAllByUserId(String userId);
}
