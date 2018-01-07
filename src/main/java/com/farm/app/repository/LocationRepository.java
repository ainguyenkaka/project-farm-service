package com.farm.app.repository;

import com.farm.app.domain.Location;
import org.springframework.data.mongodb.repository.MongoRepository;

@SuppressWarnings("unused")
public interface LocationRepository extends MongoRepository<Location, String> {
}
