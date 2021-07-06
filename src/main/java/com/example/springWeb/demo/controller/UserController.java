package com.example.springWeb.demo.controller;

import com.example.springWeb.demo.dto.BookByUserDTO;
import com.example.springWeb.demo.dto.BookDTO;
import com.example.springWeb.demo.dto.InfoUserDTO;
import com.example.springWeb.demo.model.User;
import com.example.springWeb.demo.service.BookService;
import com.example.springWeb.demo.service.OrderService;
import com.example.springWeb.demo.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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

    private Logger logger = Logger.getLogger(UserController.class);


    @Autowired
    BookService bookService;

    @Autowired
    UserService userService;

    @Autowired
    OrderService orderService;

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

    @PostMapping("/order")
    public String order(@RequestParam(name = "id") Long id, Model model) {
        orderService.saveOrder(id);
        List<BookDTO> books =  bookService.getAllBooksByFree();
        model.addAttribute("allBooks", books);
        return "user/userPage";
    }

    @GetMapping("/userInfo")
    public String userInfo(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long id_user = ((User)principal).getId();
        InfoUserDTO infoUserDTO = userService.countInfoUserDTO(id_user);

        model.addAttribute("books", infoUserDTO.getContBook());
        model.addAttribute("orders", infoUserDTO.getContOrder());
        model.addAttribute("debt", infoUserDTO.getContDebt());
        return "user/userInfo" ;
    }

    @GetMapping("/userBook")
    public String userBook(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long id_user = ((User)principal).getId();
        List<BookByUserDTO> bookByUserDTO = userService.bookByUser(id_user);
        model.addAttribute("books", bookByUserDTO);
        return "user/userBook" ;
    }
}
