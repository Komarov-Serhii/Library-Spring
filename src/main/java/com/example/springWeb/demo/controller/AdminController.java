package com.example.springWeb.demo.controller;

import com.example.springWeb.demo.dto.BookDTO;
import com.example.springWeb.demo.dto.InfoBookAndUserDTO;
import com.example.springWeb.demo.dto.OrderDTO;
import com.example.springWeb.demo.dto.UserDTO;
import com.example.springWeb.demo.model.Book;
import com.example.springWeb.demo.model.Order;
import com.example.springWeb.demo.model.User;
import com.example.springWeb.demo.service.BookService;
import com.example.springWeb.demo.service.OrderService;
import com.example.springWeb.demo.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("mainAdmin")
public class AdminController {

    @Autowired
    UserService userService;

    @Autowired
    BookService bookService;

    @Autowired
    OrderService orderService;

    private Logger logger = Logger.getLogger(AdminController.class);


    @GetMapping()
    public String showCheck(Model model) {
        InfoBookAndUserDTO statics =  userService.countInfoAboutUserAndBook();
        model.addAttribute("people", statics.getCountUser());
        model.addAttribute("active", statics.getCountActiveUser());
        model.addAttribute("blocked", statics.getCountBlockedUser());
        model.addAttribute("books", statics.getCountBook());
        model.addAttribute("booksBusy", statics.getCountBusyBook());
        model.addAttribute("order", statics.getCountOrderBook());
        return "admin/mainAdmin";
    }

    @GetMapping("/listBook")
    public String listBook(Model model) {
        List<BookDTO> bookDTOS = bookService.getAllBooksByFree();
        model.addAttribute("books", bookDTOS);

        return "admin/listBook";
    }

    @PostMapping("/listBook/search")
    public String search(@RequestParam("search") String search, Model model) {
        List<BookDTO> books = bookService.findByAuthorOrName(search);
        if (!books.isEmpty()) {
            model.addAttribute("window", true);
            model.addAttribute("list", books);
        } else {
            model.addAttribute("notFoundSearch", true);
        }

        List<BookDTO> list =  bookService.getAllBooksByFree();
        model.addAttribute("books", list);
        return "admin/listBook";
    }

    @GetMapping("/listUser")
    public String listUser(Model model) {
        List<UserDTO> userDTOS = userService.getAllReaders();
        model.addAttribute("people", userDTOS);
        return "admin/listUser";
    }

    @PostMapping("/listBook/delete")
    public String order(@RequestParam(name = "id") Long id, Model model) {
        bookService.deleteBook(id);
        List<BookDTO> bookDTOS = bookService.getAllBooksByFree();
        model.addAttribute("books", bookDTOS);
        return "admin/listBook";
    }

    @PostMapping("/listUser/block")
    public String block(@RequestParam(name = "id") Long id,@RequestParam(name = "active") boolean active, Model model) {
        logger.info(id);
        logger.info(active);

        userService.changeActive(id, active);

        List<UserDTO> userDTOS = userService.getAllReaders();
        model.addAttribute("people", userDTOS);
        return "admin/listUser";
    }

    @PostMapping("/listUser/checkBook")
    public String checkBook(@RequestParam(name = "id") Long id, Model model) {
        //List<BookDTO> list = bookService.listBookByUser(id);

        model.addAttribute("win", true);

//        if (list.isEmpty()) {
//
//        }

        List<UserDTO> userDTOS = userService.getAllReaders();
        model.addAttribute("people", userDTOS);
        return "admin/listUser";
    }

    @GetMapping("/order")
    public String order(Model model) {
        List<OrderDTO> orderDTOS = orderService.getAllOrder();
        model.addAttribute("order", orderDTOS);
        return "admin/order";
    }

    @PostMapping("/order/accept")
    public String accept(@RequestParam(name = "id") Long id,@RequestParam(name = "id_book") Long id_book, Model model) {
        orderService.acceptOrder(id, id_book);

        List<OrderDTO> orderDTOS = orderService.getAllOrder();
        model.addAttribute("order", orderDTOS);
        return "admin/order";
    }

    @PostMapping("/order/reject")
    public String reject(@RequestParam(name = "id") Long id, @RequestParam(name = "id_book") Long id_book, Model model) {
        orderService.rejectOrder(id,id_book);

        List<OrderDTO> orderDTOS = orderService.getAllOrder();
        model.addAttribute("order", orderDTOS);
        return "admin/order";
    }
}
