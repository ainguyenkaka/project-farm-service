package com.farm.app.web.rest.vm;

import com.farm.app.service.dto.UserDTO;

import javax.validation.constraints.Size;

import java.time.ZonedDateTime;
import java.util.Set;


public class ManagedUserVM extends UserDTO {

    public static final int PASSWORD_MIN_LENGTH = 4;

    public static final int PASSWORD_MAX_LENGTH = 100;

    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    private String password;

    public ManagedUserVM() {
    }

    public ManagedUserVM(String id, String login, String password,
                         String email,String phone,
                         ZonedDateTime createdDate,
                         Set<String> authorities) {

        super(id, login,  email,phone,
            createdDate, authorities);

        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "ManagedUserVM{" +
            "} " + super.toString();
    }
}
