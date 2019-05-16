package com.allaroundjava.dao;

import com.allaroundjava.model.Car;

import javax.persistence.EntityManager;
import java.util.Optional;
import java.util.function.Consumer;

public interface CarDao {
    Optional<Car> getById(Long id);

    void persist(Car item);

    void executeInTransaction(Consumer<EntityManager> consumer);

}
