package com.example.springWeb.demo.repository;

import com.example.springWeb.demo.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    Person findByName(String name);

    Optional<Person> findByEmail(String email);

    Person findByEmailAndPassword(String name, String password);

}
