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

@Document(collection = "order")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("customer_info_id")
    private String customerInfoId;

    @NotNull
    @Field("service_package_id")
    private String servicePackageId;

    @Field("created_date")
    private ZonedDateTime createdDate = ZonedDateTime.now();

    @NotNull
    @Field("status")
    private Integer status;

    @Field("is_paid")
    private Boolean isPaid;

    @NotNull
    @Field("user_id")
    private String userId;

    @Field("products")
    private Set<ChosenProduct> chosenProducts = new HashSet<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerInfoId() {
        return customerInfoId;
    }

    public Order customerInfoId(String customerInfoId) {
        this.customerInfoId = customerInfoId;
        return this;
    }

    public Set<ChosenProduct> getChosenProducts() {
        return chosenProducts;
    }

    public void setChosenProducts(Set<ChosenProduct> chosenProducts) {
        this.chosenProducts = chosenProducts;
    }

    public Boolean getPaid() {
        return isPaid;
    }

    public void setPaid(Boolean paid) {
        isPaid = paid;
    }

    public void setCustomerInfoId(String customerInfoId) {
        this.customerInfoId = customerInfoId;
    }

    public String getServicePackageId() {
        return servicePackageId;
    }

    public Order servicePackageId(String servicePackageId) {
        this.servicePackageId = servicePackageId;
        return this;
    }

    public void setServicePackageId(String servicePackageId) {
        this.servicePackageId = servicePackageId;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public Order createdDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }


    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getStatus() {
        return status;
    }

    public Order status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public Order userId(String userId) {
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
        Order order = (Order) o;
        if (order.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Order{" +
            "id=" + id +
            ", customerInfoId='" + customerInfoId + "'" +
            ", servicePackageId='" + servicePackageId + "'" +
            ", createdDate='" + createdDate + "'" +
            ", status='" + status + "'" +
            ", userId='" + userId + "'" +
            '}';
    }

    @Document(collection = "chosen_product")
    public static class ChosenProduct implements Serializable {

        public ChosenProduct() {
        }

        public ChosenProduct(String productId, Double kilogram) {
            this.productId = productId;
            this.kilogram = kilogram;
        }

        @Field("product_id")
        private String productId;

        @Field("kilogram")
        private Double kilogram;

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public Double getKilogram() {
            return kilogram;
        }

        public void setKilogram(Double kilogram) {
            this.kilogram = kilogram;
        }
    }
}
