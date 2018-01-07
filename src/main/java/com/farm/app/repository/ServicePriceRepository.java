package com.farm.app.repository;


import com.farm.app.domain.ServicePrice;
import org.springframework.data.mongodb.repository.MongoRepository;

@SuppressWarnings("unused")
public interface ServicePriceRepository extends MongoRepository<ServicePrice, String> {
}
