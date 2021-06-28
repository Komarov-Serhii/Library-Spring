package com.example.springWeb.demo.service;


import com.example.springWeb.demo.model.Person;

import java.util.List;
import java.util.Map;
import java.util.Optional;


public interface PersonService {

    Person savePerson(Person person);

    boolean deletePerson(final long personId);

    List<Person> getAllPersons();

    Person getPersonById(final long personId);

    Person findByName(String name);

    Optional<Person> findByEmail(String email);

    Person getByLoginAndPass(String name,String password);

    Map<String, Object> getHomepage(Person person);
}
