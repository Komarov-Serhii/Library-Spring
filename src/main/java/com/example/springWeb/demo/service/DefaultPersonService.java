package com.example.springWeb.demo.service;

import com.example.springWeb.demo.model.Person;
import com.example.springWeb.demo.model.Role;
import com.example.springWeb.demo.service.*;
import com.example.springWeb.demo.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.*;

@Service
public class DefaultPersonService implements PersonService {

    @Autowired
    private PersonRepository personRepository;


    @Override
    public Person savePerson(Person person) {
        return personRepository.save(person);
    }


    @Override
    public boolean deletePerson(long personId) {
        personRepository.deleteById(personId);
        return true;
    }

    @Override
    public List<Person> getAllPersons() {
        List<Person> personList = personRepository.findAll();
        return new ArrayList<>(personList);
    }

    @Override
    public Person getPersonById(long personId) {
        return personRepository.findById(personId).orElseThrow(() -> new EntityNotFoundException("Person not found"));
    }

    @Override
    public Person findByName(String name) {
        return personRepository.findByName(name);
    }

    @Override
    public Optional<Person> findByEmail(String email) {
        return personRepository.findByEmail(email);
    }

    @Override
    public Person getByLoginAndPass(String name, String password) {
        return personRepository.findByEmailAndPassword(name, password);
    }

    @Override
    public Map<String, Object> getHomepage(Person person) {
        String username = person.getName();
        Optional<Person> accountOptional = personRepository.findByEmail(username);
        Person acc = accountOptional.get();
        Map<String, Object> map = new HashMap<>();


                if(accountOptional.get().getRole() == Role.ADMIN) {
                    map.put("table", person); /// доделать
                }

        map.put("user", acc);
        return map;
    }
}
