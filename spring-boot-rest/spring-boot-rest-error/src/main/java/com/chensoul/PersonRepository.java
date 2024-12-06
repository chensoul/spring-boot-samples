package com.chensoul;

import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

interface PersonRepository extends JpaRepository<Person, Long> {

	Collection<Person> findByEmail(@Param("email") String email);

}
