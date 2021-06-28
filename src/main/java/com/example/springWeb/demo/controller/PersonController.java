package com.example.springWeb.demo.controller;

import com.example.springWeb.demo.model.Person;
import com.example.springWeb.demo.repository.PersonRepository;
import com.example.springWeb.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class PersonController {

    @Autowired
    private PersonService personService;

    @Autowired
    private PersonRepository personRepository;

    @GetMapping("/listPerson")
    public List<Person> getAllPerson() {
        return personService.getAllPersons();
    }

    @GetMapping("/person")
    public Person printPerson() {
        return personService.getPersonById(76);
    }

    @GetMapping("/personDelete/{id}")
    public boolean delete(@PathVariable long id) {
        personService.deletePerson(id);
        return true;
    }

    @GetMapping("/personByName/{name}")
    public Person findByName(@PathVariable String name) {
    return personService.findByName(name);
    }

//    @PostMapping("/registration")
//    public String addUser(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult, Model model) {
//        if (bindingResult.hasErrors()) {
//            return "registration.html";
//        }
//        if (!personService.savePerson(person)){
//            model.addAttribute("personError", "User with this name already exists!");
//            return "registration.html";
//        }
//        return "redirect:/login";
//    }


    //    @PostMapping("/login")
//    public boolean login(@ModelAttribute("person") @Valid Person person) {
//        Person person = personService.getByLoginAndPass(email, password);
//
//        if (Objects.nonNull(person)) {
//            return true;
//        }
//        return false;
//    }

}
