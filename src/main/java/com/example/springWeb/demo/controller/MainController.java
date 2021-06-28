package com.example.springWeb.demo.controller;


import com.example.springWeb.demo.dto.*;

import com.example.springWeb.demo.service.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/")
public class MainController {

    private Logger logger = Logger.getLogger(MainController.class);


    @Autowired
    PersonService personService;

    @Autowired
    BookService bookService;

    @GetMapping("/mainPage")
    public String mainPage(Model model) {
        List<BookDTO> books =  bookService.getAllBooksByFree();
        model.addAttribute("books", books);
        return "index";
    }



    @GetMapping ("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/sort")
    public String sort(@RequestParam("sort") String sort, Model model) {
        model.addAttribute("books",  bookService.sort(sort));
        System.out.println(sort);
        return "index";
    }

    @PostMapping ("/search")
    public String search(@RequestParam("search") String search, Model model) {
        List<BookDTO> books = bookService.findByAuthorOrName(search);
        if (books.isEmpty()) {
            model.addAttribute("notFoundSearch", true);
        }
        model.addAttribute("successfulFound", true);
        model.addAttribute("searchBooks", books);
        return "index";
    }

    @GetMapping ("/registration")
    public String registrationWithLog() {
        return "registration";
    }

//    @PostMapping("/registration")
//    public String addUser(@ModelAttribute("usr") @Valid Person person, BindingResult bindingResult, Model model) {
//        if (bindingResult.hasErrors()) {
//            return "registration.html";
//        }
//
//        if (!personService.savePerson(person)){
//            model.addAttribute("usernameError", "Пользователь с таким именем уже существует");
//            return "registration.html";
//        }
//        return "redirect:/login";
//    }
}
