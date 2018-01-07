package com.farm.app.web.rest.app;

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
@RequestMapping("/api/app")
public class AppOrderResource {
    private final Logger log = LoggerFactory.getLogger(AppOrderResource.class);

    private static final String ENTITY_NAME = "order";

    private final OrderService orderService;

    public AppOrderResource(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/orders")
    @Timed
    public ResponseEntity<Order> createOrder(@Valid @RequestBody Order order,@RequestParam String userId) throws URISyntaxException {
        log.debug("REST request to save Order : {}", order);
        if (order.getId() != null || userId == null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new order cannot already have an ID")).body(null);
        }
        order.setUserId(userId);
        Order result = orderService.save(order);
        return ResponseEntity.created(new URI("/api/app/orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @GetMapping("/orders")
    @Timed
    public List<Order> getAllOrders(@RequestParam String userId) {
        log.debug("REST request to get all Orders");
        return orderService.findAllByUserId(userId);
    }


    @GetMapping("/orders/{id}")
    @Timed
    public ResponseEntity<Order> getOrder(@PathVariable String id,@RequestParam String userId) {
        log.debug("REST request to get Order : {} with user: {}", id,userId);
        if (userId == null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "useridexists", "Must have userId param")).body(null);
        }
        Order order = orderService.findOneByUserId(id,userId);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(order));
    }

    @DeleteMapping("/orders/{id}")
    @Timed
    public ResponseEntity<Void> deleteOrder(@PathVariable String id, @RequestParam String userId) {
        log.debug("REST request to delete Order : {} with user: {}", id, userId);
        if (userId == null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "useridexists", "Must have userId param")).body(null);
        }
        if(orderService.findOneByUserId(id,userId) == null){
            return ResponseEntity.notFound().build();
        }
        orderService.delete(id,userId);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
