package com.farm.app.repository;

import com.farm.app.domain.CustomerInfo;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


@SuppressWarnings("unused")
public interface CustomerInfoRepository extends MongoRepository<CustomerInfo,String> {

    CustomerInfo findOneByIdAndUserId(String id,String userId);

    List<CustomerInfo> findAllByUserId(String userId);
}
