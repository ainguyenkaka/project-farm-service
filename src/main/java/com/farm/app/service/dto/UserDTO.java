package com.farm.app.service.dto;

import com.farm.app.config.Constants;

import com.farm.app.domain.Authority;
import com.farm.app.domain.User;
import org.hibernate.validator.constraints.Email;


import javax.validation.constraints.*;
import java.time.ZonedDateTime;
import java.util.Set;
import java.util.stream.Collectors;


public class UserDTO {

    private String id;

    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1, max = 50)
    private String login;

    @Size(min = 1, max = 15)
    private String phone;

    @Email
    @Size(min = 5, max = 100)
    private String email;

    private ZonedDateTime createdDate = ZonedDateTime.now();


    private Set<String> authorities;

    public UserDTO() {

    }

    public UserDTO(User user) {
        this(user.getId(), user.getLogin(),
            user.getEmail(), user.getPhone(), user.getCreatedDate(),
            user.getAuthorities().stream().map(Authority::getName)
                .collect(Collectors.toSet()));
    }

    public UserDTO(String id, String login,
                   String email, String phone, ZonedDateTime createdDate,
                   Set<String> authorities) {

        this.id = id;
        this.login = login;
        this.phone = phone;
        this.email = email;
        this.createdDate = createdDate;
        this.authorities = authorities;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
            "login='" + login + '\'' +
            ", email='" + email + '\'' +
            ", authorities=" + authorities +
            "}";
    }
}
