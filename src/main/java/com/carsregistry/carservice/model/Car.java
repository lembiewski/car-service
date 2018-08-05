package com.carsregistry.carservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@Builder
public class Car {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String make;

    @Column
    private String model;

    @Column
    private int year;

    @Column
    @Enumerated(EnumType.STRING)
    private Color color;

    @Column
    private String vin;

    @Column
    private String plateNumber;

    @Column
    @Enumerated(EnumType.STRING)
    private VehicleType type;

    public Car() {
    }
}
