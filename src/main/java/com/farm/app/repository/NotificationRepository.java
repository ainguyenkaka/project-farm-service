package com.farm.app.repository;

import com.farm.app.domain.Notification;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

@SuppressWarnings("unused")
public interface NotificationRepository extends MongoRepository<Notification,String> {

    List<Notification> findAllBySentUsersContains(String userId);

    Notification findOneByIdAndSentUsersContains(String id,String userId);
}
