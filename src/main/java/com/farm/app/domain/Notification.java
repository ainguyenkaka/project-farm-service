package com.farm.app.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Document(collection = "notification")
public class Notification implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Size(min = 3)
    @Field("title")
    private String title;

    @NotNull
    @Size(min = 3)
    @Field("message")
    private String message;

    @Field("created_date")
    private ZonedDateTime createdDate = ZonedDateTime.now();

    @Field("sent_users")
    private Set<String> sentUsers = new HashSet<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Notification title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public Notification message(String message) {
        this.message = message;
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public Notification createdDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Notification notification = (Notification) o;
        if (notification.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, notification.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public Set<String> getSentUsers() {
        return sentUsers;
    }

    public void setSentUsers(Set<String> sentUsers) {
        this.sentUsers = sentUsers;
    }

    @Override
    public String toString() {
        return "Notification{" +
            "id=" + id +
            ", title='" + title + "'" +
            ", message='" + message + "'" +
            ", createdDate='" + createdDate + "'" +
            '}';
    }
}
