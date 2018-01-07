package com.farm.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.farm.app.domain.Order;
import com.farm.app.service.OrderService;
import com.farm.app.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class OrderResource {

    private final Logger log = LoggerFactory.getLogger(OrderResource.class);

    private static final String ENTITY_NAME = "order";

    private final OrderService orderService;

    public OrderResource(OrderService orderService) {
        this.orderService = orderService;
    }


    @PostMapping("/orders")
    @Timed
    public ResponseEntity<Order> createOrder(@Valid @RequestBody Order order) throws URISyntaxException {
        log.debug("REST request to save Order : {}", order);
        if (order.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new order cannot already have an ID")).body(null);
        }
        Order result = orderService.save(order);
        return ResponseEntity.created(new URI("/api/orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }


    @PutMapping("/orders")
    @Timed
    public ResponseEntity<Order> updateOrder(@Valid @RequestBody Order order) throws URISyntaxException {
        log.debug("REST request to update Order : {}", order);
        if (order.getId() == null) {
            return createOrder(order);
        }
        Order result = orderService.save(order);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, order.getId().toString()))
            .body(result);
    }


    @GetMapping("/orders")
    @Timed
    public List<Order> getAllOrders() {
        log.debug("REST request to get all Orders");
        return orderService.findAll();
    }


    @GetMapping("/orders/{id}")
    @Timed
    public ResponseEntity<Order> getOrder(@PathVariable String id) {
        log.debug("REST request to get Order : {}", id);
        Order order = orderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(order));
    }


    @DeleteMapping("/orders/{id}")
    @Timed
    public ResponseEntity<Void> deleteOrder(@PathVariable String id) {
        log.debug("REST request to delete Order : {}", id);
        orderService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
