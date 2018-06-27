package com.carservice.model;

public final class CarBuilder {
    private Long id;
    private String vin;
    private String plateNumber;

    private CarBuilder() {
    }

    public static CarBuilder aCar() {
        return new CarBuilder();
    }

    public CarBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public CarBuilder withVin(String vin) {
        this.vin = vin;
        return this;
    }

    public CarBuilder withPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
        return this;
    }

    public Car build() {
        Car car = new Car();
        car.setId(id);
        car.setVin(vin);
        car.setPlateNumber(plateNumber);
        return car;
    }
}
