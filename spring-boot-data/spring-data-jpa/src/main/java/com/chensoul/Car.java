package com.chensoul;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Car {

    @Id
    @GeneratedValue
    private long id;

    private String make;

    private String model;

    private int year;

    public Car() {
    }

    public Car(String make, String model, int year) {
        super();
        this.make = make;
        this.model = model;
        this.year = year;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    @Override
    public String toString() {
        return make + " " + model + " " + year;
    }

}
