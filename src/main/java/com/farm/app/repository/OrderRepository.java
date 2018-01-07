package com.farm.app.repository;

import com.farm.app.domain.Order;

import org.aspectj.weaver.ast.Or;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


@SuppressWarnings("unused")
public interface OrderRepository extends MongoRepository<Order,String> {
    List<Order> findAllByUserId(String userId);

    Order findOneByIdAndUserId(String id, String userId);

    void deleteByIdAndUserId(String id, String userId);
}
