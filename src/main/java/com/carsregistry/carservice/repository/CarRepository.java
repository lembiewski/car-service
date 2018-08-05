package com.carsregistry.carservice.repository;

import com.carsregistry.carservice.model.Car;
import org.springframework.data.repository.CrudRepository;

public interface CarRepository extends CrudRepository<Car, Long> {
}
