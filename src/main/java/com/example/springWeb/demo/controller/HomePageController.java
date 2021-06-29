package com.example.springWeb.demo.controller;

import com.example.springWeb.demo.model.User;
import com.example.springWeb.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomePageController {


    @GetMapping("/personPage")
    public String getHomePage() {
        return "personPage";
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
