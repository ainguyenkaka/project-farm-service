package com.farm.app.domain;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Document(collection = "service_price")
public class ServicePrice {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("average_price")
    @Min(0)
    private Double averagePrice;

    @NotNull
    @Field("advanced_location_price")
    @Min(0)
    private Double advancedLocationPrice;

    @NotNull
    @Field("advanced_product_price")
    @Min(0)
    private Double advancedProductPrice;

    @NotNull
    @Field("advanced_delivery_price")
    @Min(0)
    private Double advancedDeliveryPrice;

    public ServicePrice() {
    }

    public ServicePrice(Double averagePrice, Double advancedLocationPrice, Double advancedProductPrice, Double advancedDeliveryPrice) {
        this.averagePrice = averagePrice;
        this.advancedLocationPrice = advancedLocationPrice;
        this.advancedProductPrice = advancedProductPrice;
        this.advancedDeliveryPrice = advancedDeliveryPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(Double averagePrice) {
        this.averagePrice = averagePrice;
    }

    public Double getAdvancedLocationPrice() {
        return advancedLocationPrice;
    }

    public void setAdvancedLocationPrice(Double advancedLocationPrice) {
        this.advancedLocationPrice = advancedLocationPrice;
    }

    public Double getAdvancedProductPrice() {
        return advancedProductPrice;
    }

    public void setAdvancedProductPrice(Double advancedProductPrice) {
        this.advancedProductPrice = advancedProductPrice;
    }

    public Double getAdvancedDeliveryPrice() {
        return advancedDeliveryPrice;
    }

    public void setAdvancedDeliveryPrice(Double advancedDeliveryPrice) {
        this.advancedDeliveryPrice = advancedDeliveryPrice;
    }
}
