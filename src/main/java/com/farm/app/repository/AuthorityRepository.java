package com.farm.app.repository;

import com.farm.app.domain.Authority;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AuthorityRepository extends MongoRepository<Authority, String> {
}
