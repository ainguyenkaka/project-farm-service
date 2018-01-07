package com.farm.app.service;

import com.farm.app.domain.Order;
import com.farm.app.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final Logger log = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    public Order save(Order order) {
        log.debug("Request to save Order : {}", order);
        Order result = orderRepository.save(order);
        return result;
    }


    public List<Order> findAll() {
        log.debug("Request to get all Orders");
        List<Order> result = orderRepository.findAll();

        return result;
    }


    public Order findOne(String id) {
        log.debug("Request to get Order : {}", id);
        Order order = orderRepository.findOne(id);
        return order;
    }

    public void delete(String id) {
        log.debug("Request to delete Order : {}", id);
        orderRepository.delete(id);
    }

    public List<Order> findAllByUserId(String userId) {
        log.debug("Request to get all Orders by user");
        List<Order> result = orderRepository.findAllByUserId(userId);

        return result;
    }

    public Order findOneByUserId(String id, String userId) {
        log.debug("Request to get Order : {} and user {}", id,userId);
        Order order = orderRepository.findOneByIdAndUserId(id,userId);
        return order;
    }

    public void delete(String id, String userId) {
        log.debug("Request to delete Order : {} with userId: {}", id, userId);
        orderRepository.deleteByIdAndUserId(id,userId);
    }
}
