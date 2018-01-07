package com.farm.app.domain;

import org.hibernate.validator.constraints.Email;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;


@Document(collection = "farm")
public class Farm implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Size(min = 3)
    @Field("title")
    private String title;

    @NotNull
    @DecimalMin(value = "0")
    @Field("acreage")
    private Double acreage;

    @NotNull
    @Size(min = 3)
    @Field("description")
    private String description;

    @NotNull
    @Size(min = 3)
    @Field("contact")
    private String contact;

    @NotNull
    @Email
    @Field("email")
    private String email;

    @NotNull
    @Size(min = 3)
    @Field("phone")
    private String phone;

    @Field("image_url")
    private String imageUrl;

    @Field("methods")
    private Set<String> methods = new HashSet<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Farm title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getAcreage() {
        return acreage;
    }

    public Farm acreage(Double acreage) {
        this.acreage = acreage;
        return this;
    }

    public void setAcreage(Double acreage) {
        this.acreage = acreage;
    }

    public String getDescription() {
        return description;
    }

    public Farm description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContact() {
        return contact;
    }

    public Farm contact(String contact) {
        this.contact = contact;
        return this;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public Farm email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public Farm phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Farm imageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Farm farm = (Farm) o;
        if (farm.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, farm.id);
    }


    public Set<String> getMethods() {
        return methods;
    }

    public void setMethods(Set<String> methods) {
        this.methods = methods;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Farm{" +
            "id=" + id +
            ", title='" + title + "'" +
            ", acreage='" + acreage + "'" +
            ", description='" + description + "'" +
            ", contact='" + contact + "'" +
            ", email='" + email + "'" +
            ", phone='" + phone + "'" +
            ", imageUrl='" + imageUrl + "'" +
            '}';
    }
}
