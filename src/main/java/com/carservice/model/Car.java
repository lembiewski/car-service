package com.carservice.model;

import javax.persistence.*;

@Entity
public class Car {

    @Id
    @Column
    @GeneratedValue
    private Long id;

    @Column
    private String vin;

    @Column
    private String plateNumber;

    @Column
    @Enumerated(EnumType.STRING)
    private VehicleType type;

    public Long getId() {
        return id;
    }

    void setId(Long id) {
        this.id = id;
    }

    public String getVin() {
        return vin;
    }

    void setVin(String vin) {
        this.vin = vin;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public VehicleType getType() {
        return type;
    }

    void setType(VehicleType type) {
        this.type = type;
    }
}
