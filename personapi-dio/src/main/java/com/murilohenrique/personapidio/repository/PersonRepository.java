package com.murilohenrique.personapidio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.murilohenrique.personapidio.entity.Person;


public interface PersonRepository extends JpaRepository<Person, Long>{

}
