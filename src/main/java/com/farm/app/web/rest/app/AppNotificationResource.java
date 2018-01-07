package com.farm.app.web.rest.app;

import com.codahale.metrics.annotation.Timed;
import com.farm.app.domain.Notification;
import com.farm.app.service.NotificationService;
import com.farm.app.web.rest.NotificationResource;
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
public class AppNotificationResource {

    private final Logger log = LoggerFactory.getLogger(NotificationResource.class);

    private static final String ENTITY_NAME = "notification";

    private final NotificationService notificationService;

    public AppNotificationResource(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/notifications/{id}")
    @Timed
    public ResponseEntity<Notification> getNotification(@PathVariable String id, @RequestParam String userId) {
        log.debug("REST request to get Notification : {}", id);
        if (userId == null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "useridexists", "Must have userId param")).body(null);
        }
        Notification notification = notificationService.findOneByUser(id,userId);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(notification));
    }


    @GetMapping("/notifications")
    @Timed
    public List<Notification> getAllNotifications( @RequestParam String userId) {
        log.debug("REST request to get all Notifications by user");
        return notificationService.findAllByUser(userId);
    }


    @PostMapping("/notifications")
    @Timed
    public ResponseEntity<Notification> createNotification(@Valid @RequestBody Notification notification, @RequestParam String userId) throws URISyntaxException {
        log.debug("REST request to save Notification : {}", notification);
        if (notification.getId() != null || userId == null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new notification cannot already have an ID and Must have userId param")).body(null);
        }
        notification.getSentUsers().add(userId);
        Notification result = notificationService.save(notification);
        return ResponseEntity.created(new URI("/api/app/notifications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
}
