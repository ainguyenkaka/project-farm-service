package com.farm.app.domain;

import org.hibernate.validator.constraints.Email;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;


@Document(collection = "customer_info")
public class CustomerInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Size(min = 3)
    @Field("name")
    private String name;

    @Email
    @Size(min = 5, max = 100)
    private String email;

    @NotNull
    @Size(min = 3)
    @Field("phone")
    private String phone;

    @NotNull
    @Size(min = 3)
    @Field("organization")
    private String organization;

    @Field("ward")
    private String ward;

    @Field("town")
    private String town;

    @Field("street")
    private String street;

    @NotNull
    @Field("house_number")
    private String houseNumber;

    @NotNull
    @Min(value = 0)
    @Field("members")
    private Integer members;

    @NotNull
    @Field("user_id")
    private String userId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public CustomerInfo name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public CustomerInfo phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOrganization() {
        return organization;
    }

    public CustomerInfo organization(String organization) {
        this.organization = organization;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getStreet() {
        return street;
    }

    public CustomerInfo street(String street) {
        this.street = street;
        return this;
    }

    public CustomerInfo houseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
        return this;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public Integer getMembers() {
        return members;
    }

    public CustomerInfo members(Integer members) {
        this.members = members;
        return this;
    }



    public CustomerInfo town(String town) {
        this.town = town;
        return this;
    }

    public void setMembers(Integer members) {
        this.members = members;
    }

    public String getUserId() {
        return userId;
    }

    public CustomerInfo userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CustomerInfo customerInfo = (CustomerInfo) o;
        if (customerInfo.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, customerInfo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CustomerInfo{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", phone='" + phone + "'" +
            ", organization='" + organization + "'" +
            ", town='" + town + "'" +
            ", ward='" + ward + "'" +
            ", street='" + street + "'" +
            ", houseNumber='" + houseNumber + "'" +
            ", members='" + members + "'" +
            ", userId='" + userId + "'" +
            '}';
    }



}
