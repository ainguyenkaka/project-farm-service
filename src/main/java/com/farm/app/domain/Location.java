package com.farm.app.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.HashSet;
import java.util.Set;

@Document(collection = "location")
public class Location {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("basic_locations")
    private Set<String> basicLocations = new HashSet<>();


    public Location() {
    }

    public void addBasicLocation(String location) {
        if (!basicLocations.contains(location))
            basicLocations.add(location);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Set<String> getBasicLocations() {
        return basicLocations;
    }

    public void setBasicLocations(Set<String> basicLocations) {
        this.basicLocations = basicLocations;
    }
}
