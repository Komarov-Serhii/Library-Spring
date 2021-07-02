package com.example.springWeb.demo.controller;


import com.example.springWeb.demo.dto.*;

import com.example.springWeb.demo.model.User;
import com.example.springWeb.demo.service.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
public class MainController {

    private Logger logger = Logger.getLogger(MainController.class);


    @Autowired
    UserService userService;

    @Autowired
    BookService bookService;

    @GetMapping("/successLogin")
    public void loginPageRedirect(HttpServletResponse response, Authentication authResult) throws IOException {

        String role = authResult.getAuthorities().toString();

        if (role.contains("ROLE_ADMIN")) {
            response.sendRedirect(response.encodeRedirectURL("/mainAdmin"));
        } else {
            response.sendRedirect(response.encodeRedirectURL("/userPage"));
        }
    }

    @GetMapping("/mainPage")
    public String mainPage(Model model) {
        List<BookDTO> books =  bookService.getAllBooksByFree();
        model.addAttribute("allBooks", books);
        return "index";
    }

    @GetMapping("/sort")
    public String sort(@RequestParam("sort") String sort, Model model) {
        model.addAttribute("allBooks",  bookService.sort(sort));
        return "index";
    }

    @PostMapping ("/search")
    public String search(@RequestParam("search") String search, Model model) {
        List<BookDTO> books = bookService.findByAuthorOrName(search);
        if (!books.isEmpty()) {
            model.addAttribute("successfulFound", true);
            model.addAttribute("searchBooks", books);
        } else {
            model.addAttribute("notFoundSearch", true);
        }

        List<BookDTO> list =  bookService.getAllBooksByFree();
        model.addAttribute("allBooks", list);
        return "index";
    }

    @GetMapping ("/registration")
    public String registrationWithLog() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute("userForm") @Valid User userForm, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        if (!userService.saveUser(userForm)){
            model.addAttribute("alreadyExist", true);
            return "registration";
        }

        return "redirect:/login";
    }
}
