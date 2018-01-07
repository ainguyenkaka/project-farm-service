package com.farm.app.service;

import com.farm.app.domain.Notification;
import com.farm.app.repository.NotificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    private final Logger log = LoggerFactory.getLogger(NotificationService.class);

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public Notification save(Notification notification) {
        log.debug("Request to save Notification : {}", notification);
        Notification result = notificationRepository.save(notification);
        return result;
    }


    public List<Notification> findAll() {
        log.debug("Request to get all Notifications");
        List<Notification> result = notificationRepository.findAll();

        return result;
    }

    public Notification findOne(String id) {
        log.debug("Request to get Notification : {}", id);
        Notification notification = notificationRepository.findOne(id);
        return notification;
    }


    public void delete(String id) {
        log.debug("Request to delete Notification : {}", id);
        notificationRepository.delete(id);
    }

    public List<Notification> findAllByUser(String userId) {
        log.debug("Request to get all Notifications by Users");
        List<Notification> result = notificationRepository.findAllBySentUsersContains(userId);

        return result;
    }

    public Notification findOneByUser(String id, String userId) {
        log.debug("Request to get Notification : {} and user {}", id, userId);
        Notification notification = notificationRepository.findOneByIdAndSentUsersContains(id,userId);
        return notification;
    }
}
