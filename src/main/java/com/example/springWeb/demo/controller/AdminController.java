package com.example.springWeb.demo.controller;

import com.example.springWeb.demo.dto.InfoBookAndUserDTO;
import com.example.springWeb.demo.dto.OrderDTO;
import com.example.springWeb.demo.dto.UserDTO;
import com.example.springWeb.demo.service.OrderService;
import com.example.springWeb.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("mainAdmin")
public class AdminController {

    @Autowired
    UserService userService;

    @Autowired
    OrderService orderService;

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
    public String listBook() {

        return "admin/listBook";
    }
    @GetMapping("/listUser")
    public String listUser(Model model) {
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
}
