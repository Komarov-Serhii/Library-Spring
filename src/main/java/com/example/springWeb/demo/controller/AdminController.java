package com.example.springWeb.demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("adminPage")
public class AdminController {
    @GetMapping
    public String showCheck() {
        return "adminPage";
    }
}
