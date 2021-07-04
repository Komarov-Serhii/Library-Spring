package com.example.springWeb.demo.controller;

import com.example.springWeb.demo.dto.BookDTO;
import com.example.springWeb.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("userPage")
public class UserController {

    @Autowired
    BookService bookService;

    @GetMapping
    public String showCheck(Model model) {
        List<BookDTO> books =  bookService.getAllBooksByFree();
        model.addAttribute("allBooks", books);
        return "user/userPage";
    }

    @GetMapping("/sort")
    public String sort(@RequestParam("sort") String sort, Model model) {
        model.addAttribute("allBooks",  bookService.sort(sort));
        return "user/userPage";
    }

    @PostMapping("/search")
    public String search(@RequestParam("search") String search, Model model) {
        List<BookDTO> books = bookService.findByAuthorOrName(search);
        if (!books.isEmpty()) {
            model.addAttribute("successfulFound", true);
            model.addAttribute("searchBooks", books);
        } else {
            model.addAttribute("notFoundSearchInUserPage", true);
        }

        List<BookDTO> list =  bookService.getAllBooksByFree();
        model.addAttribute("allBooks", list);
        return "user/userPage";
    }

    @GetMapping("/userInfo")
    public String userInfo() {
        return "user/userInfo" ;
    }
}
