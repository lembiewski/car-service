package com.carservice.controller;

import com.carservice.model.Car;
import com.carservice.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/cars")
public class CarController {

    private CarRepository carRepository;

    @Autowired
    public CarController(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @GetMapping
    public Iterable<Car> getCars() {
        return carRepository.findAll();
    }

    @GetMapping("/{id}")
    public Car getCarById(@PathVariable Long id) {
        Objects.requireNonNull(id, "Id can't be null.");
        Optional<Car> car = carRepository.findById(id);
        return car.orElseThrow(() -> new IllegalArgumentException("Car with id '" + id + "' are not found."));
    }

    @PostMapping
    @PutMapping
    public Car createCar(@RequestBody Car car) {
        Objects.requireNonNull(car);
        return carRepository.save(car);
    }
}
