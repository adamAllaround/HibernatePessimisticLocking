package com.allaroundjava.dao;

import com.allaroundjava.model.Car;
import com.anarsoft.vmlens.concurrent.junit.ConcurrentTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.persistence.EntityManagerFactory;
import javax.persistence.LockModeType;
import javax.persistence.Persistence;
import java.util.List;
import java.util.stream.IntStream;

@RunWith(ConcurrentTestRunner.class)
public class CarDaoImplTest {
    private CarDao carDao;
    private EntityManagerFactory emf;

    public CarDaoImplTest() {
        emf = Persistence.createEntityManagerFactory("hibernatePessimisticLocking");
        carDao = new CarDaoImpl(emf);
    }

    @Test
    @com.anarsoft.vmlens.concurrent.junit.ThreadCount(3)
    public void whenExecutingQuery_thenRowsLockedMultithreaded() {
        persistRandomCars(10);

        carDao.executeInTransaction(entityManager -> {
            List<Car> cars = entityManager.createQuery("select c from Car c", Car.class)
                    .setLockMode(LockModeType.PESSIMISTIC_WRITE)
                    .getResultList();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("there are " + cars.size() + " cars");
        });
    }

    @Test
    public void whenExecutingQuery_thenRowsLocked() {
        persistRandomCars(10);

        carDao.executeInTransaction(entityManager -> {
            List<Car> cars = entityManager.createQuery("select c from Car c", Car.class)
                    .setLockMode(LockModeType.PESSIMISTIC_WRITE)
                    .getResultList();
            System.out.println("there are " + cars.size() + " cars");
        });
    }

    @Test
    public void whenQueryingSingleElement_thenRowsLocked() {
        persistRandomCars(10);

        carDao.executeInTransaction(entityManager ->
                entityManager.find(Car.class, 1L, LockModeType.PESSIMISTIC_READ));

    }

    private void persistRandomCars(int howMany) {
        IntStream.range(0, howMany).forEach(this::persistCar);
    }

    private void persistCar(int i) {
        Car car = new Car();
        car.setMake("Ford");
        car.setModel("Fiesta " + i);
        car.setMileageKm(10000L + (i * 10));
        carDao.persist(car);
    }
}