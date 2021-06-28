package com.example.springWeb.demo.controller;

import com.example.springWeb.demo.model.Person;
import com.example.springWeb.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/homePage")
public class HomePageController {

    @Autowired
    PersonService personService;

    @GetMapping("/")
    public String getHomePage(Model model) {
//        var person = (Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        Person personNew = personService.getPersonById(person.getId());
//        model.addAttribute("table");
//        return "/homePage/" + personNew.getRole().name().toLowerCase();

        var person = (Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String, Object> map = personService.getHomepage(person);
        Person personNew = (Person) map.get("user");
        model.addAttribute("table", map.get("table"));
        return "/homePage/"  + personNew.getRole().name().toLowerCase();

    }


//    @GetMapping("/")
//    public String getHomePage(Model model) {
//        var userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        Map<String, Object> map = homepageService.getHomepage(userDetails);
//        Account acc = (Account) map.get(USER);
//        model.addAttribute("table", map.get("table"));
//        return HOMEPAGE + acc.getRole().name().toLowerCase() + "home";
//    }
}
