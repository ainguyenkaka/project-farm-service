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


@Document(collection = "service_package")
public class ServicePackage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("user_id")
    private String userId;

    @NotNull
    @Size(min = 3)
    @Field("category")
    private String category;

    @NotNull
    @Size(min = 3)
    @Field("pay_type")
    private String payType;

    @Size(min = 3)
    @Field("order_type")
    private String orderType;

    @Field("delivery_per_week")
    private Integer deliveryPerWeek;

    @Field("kilogram_per_time")
    private Integer kilogramPerTime;

    @NotNull
    @DecimalMin(value = "0")
    @Field("total_kilogram")
    private Double totalKilogram;

    @NotNull
    @Min(value = 0)
    @Field("user_number")
    private Integer userNumber;

    @NotNull
    @DecimalMin(value = "0")
    @Field("total_price")
    private Double totalPrice;

    @NotNull
    @Min(value = 0)
    @Field("before_month")
    private Integer beforeMonth;

    @Field("start_date")
    private ZonedDateTime startDate;

    @Field("end_date")
    private ZonedDateTime endDate;

    @Field("days")
    private Set<Integer> days = new HashSet<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public ServicePackage userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCategory() {
        return category;
    }

    public ServicePackage category(String category) {
        this.category = category;
        return this;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPayType() {
        return payType;
    }

    public ServicePackage payType(String payType) {
        this.payType = payType;
        return this;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Integer getKilogramPerTime() {
        return kilogramPerTime;
    }

    public void setKilogramPerTime(Integer kilogramPerTime) {
        this.kilogramPerTime = kilogramPerTime;
    }

    public Double getTotalKilogram() {
        return totalKilogram;
    }

    public ServicePackage totalKilogram(Double totalKilogram) {
        this.totalKilogram = totalKilogram;
        return this;
    }

    public void setTotalKilogram(Double totalKilogram) {
        this.totalKilogram = totalKilogram;
    }

    public Integer getUserNumber() {
        return userNumber;
    }

    public ServicePackage userNumber(Integer userNumber) {
        this.userNumber = userNumber;
        return this;
    }

    public void setUserNumber(Integer userNumber) {
        this.userNumber = userNumber;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public ServicePackage totalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public Integer getDeliveryPerWeek() {
        return deliveryPerWeek;
    }

    public void setDeliveryPerWeek(Integer deliveryPerWeek) {
        this.deliveryPerWeek = deliveryPerWeek;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getBeforeMonth() {
        return beforeMonth;
    }

    public ServicePackage beforeMonth(Integer beforeMonth) {
        this.beforeMonth = beforeMonth;
        return this;
    }

    public void setBeforeMonth(Integer beforeMonth) {
        this.beforeMonth = beforeMonth;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public ServicePackage startDate(ZonedDateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public ServicePackage endDate(ZonedDateTime endDate) {
        this.endDate = endDate;
        return this;
    }

    public Set<Integer> getDays() {
        return days;
    }

    public void setDays(Set<Integer> days) {
        this.days = days;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ServicePackage servicePackage = (ServicePackage) o;
        if (servicePackage.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, servicePackage.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ServicePackage{" +
            "id=" + id +
            ", userId='" + userId + "'" +
            ", category='" + category + "'" +
            ", payType='" + payType + "'" +
            ", totalKilogram='" + totalKilogram + "'" +
            ", userNumber='" + userNumber + "'" +
            ", totalPrice='" + totalPrice + "'" +
            ", beforeMonth='" + beforeMonth + "'" +
            ", startDate='" + startDate + "'" +
            ", endDate='" + endDate + "'" +
            '}';
    }


}
