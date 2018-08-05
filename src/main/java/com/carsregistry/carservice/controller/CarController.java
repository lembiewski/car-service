package com.carsregistry.carservice.controller;

import com.carsregistry.carservice.exception.CarNotFoundException;
import com.carsregistry.carservice.model.Car;
import com.carsregistry.carservice.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Objects;

@RestController
@RequestMapping("/v1/cars")
public class CarController {

    private CarRepository carRepository;

    @Autowired
    public CarController(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @RequestMapping(method = RequestMethod.OPTIONS)
    ResponseEntity<?> options() {

        //@formatter:off
        return ResponseEntity
                .ok()
                .allow(HttpMethod.GET, HttpMethod.POST,
                        HttpMethod.HEAD, HttpMethod.OPTIONS,
                        HttpMethod.PUT, HttpMethod.DELETE)
                .build();
        //@formatter:on
    }

    @GetMapping
    public ResponseEntity<Iterable<Car>> getCars() {
        return ResponseEntity.ok(carRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable Long id) {
        Objects.requireNonNull(id, "Id can't be null.");
        return carRepository.findById(id).map(ResponseEntity::ok)
                   .orElseThrow(() -> new CarNotFoundException(id));

    }

    @GetMapping("/hello")
    public String sayHi(@Value("${message}") String message) {

        System.out.println("Sending hello");
        return message;
    }

    @PostMapping
    public ResponseEntity<Car> createCar(@RequestBody Car c) {
        Car car = this.carRepository.save(Car.builder()
                .color(c.getColor())
                .make(c.getMake())
                .plateNumber(c.getPlateNumber())
                .model(c.getModel())
                .type(c.getType())
                .vin(c.getVin())
                .year(c.getYear())
                .build());
        URI uri = MvcUriComponentsBuilder.fromController(getClass()).path("/{id}")
                .buildAndExpand(car.getId()).toUri();
        return ResponseEntity.created(uri).body(car);
    }

    @DeleteMapping(value = "/{id}")
    ResponseEntity<?> delete(@PathVariable Long id) {
        return this.carRepository.findById(id).map(c -> {
            carRepository.delete(c);
            return ResponseEntity.noContent().build();
        }).orElseThrow(() -> new CarNotFoundException(id));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
    ResponseEntity<?> head(@PathVariable Long id) {
        return this.carRepository.findById(id)
                .map(exists -> ResponseEntity.noContent().build())
                .orElseThrow(() -> new CarNotFoundException(id));
    }

    @PutMapping(value = "/{id}")
    ResponseEntity<Car> put(@PathVariable Long id, @RequestBody Car c) {
        return this.carRepository
                .findById(id)
                .map(existing -> {
                        Car car = this.carRepository.save(Car.builder()
                                .id(existing.getId())
                                .color(c.getColor())
                                .make(c.getMake())
                                .plateNumber(c.getPlateNumber())
                                .model(c.getModel())
                                .type(c.getType())
                                .vin(c.getVin())
                                .year(c.getYear())
                                .build());
                        URI selfLink = URI.create(ServletUriComponentsBuilder.fromCurrentRequest()
                                .toUriString());
                        return ResponseEntity.created(selfLink).body(car);
                    }).orElseThrow(() -> new CarNotFoundException(id));

    }

}
