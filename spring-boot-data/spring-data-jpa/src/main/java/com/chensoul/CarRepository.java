package com.chensoul;

import org.springframework.data.repository.CrudRepository;

public interface CarRepository extends CrudRepository<Car, Long> {

	Iterable<Car> findByMakeIgnoringCase(String make);

}
