package com.example.springWeb.demo.controller;

import com.example.springWeb.demo.dto.BookByUserDTO;
import com.example.springWeb.demo.dto.BookDTO;
import com.example.springWeb.demo.dto.InfoUserDTO;
import com.example.springWeb.demo.dto.UserForProfileDTO;
import com.example.springWeb.demo.model.User;
import com.example.springWeb.demo.service.BookService;
import com.example.springWeb.demo.service.OrderService;
import com.example.springWeb.demo.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long id_user = ((User)principal).getId();
        logger.info(id_user);
        List<BookDTO> books = bookService.getAllBooksByFreeAndCheckDebt(id_user);
        model.addAttribute("allBooks", books);
        return "user/userPage";
    }

    @GetMapping("/sort")
    public String sort(@RequestParam("sort") String sort, Model model) {
        model.addAttribute("allBooks", bookService.sort(sort));
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

        List<BookDTO> list = bookService.getAllBooksByFree();
        model.addAttribute("allBooks", list);
        return "user/userPage";
    }

    @PostMapping("/order")
    public String order(@RequestParam(name = "id") Long id, Model model) {
        orderService.saveOrder(id);
        List<BookDTO> books = bookService.getAllBooksByFree();
        model.addAttribute("allBooks", books);
        return "user/userPage";
    }

    @GetMapping("/userInfo")
    public String userInfo(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long id_user = ((User) principal).getId();
        InfoUserDTO infoUserDTO = userService.countInfoUserDTO(id_user);

        model.addAttribute("books", infoUserDTO.getContBook());
        model.addAttribute("orders", infoUserDTO.getContOrder());
        model.addAttribute("debt", infoUserDTO.getContDebt());
        return "user/userInfo";
    }

    @GetMapping("/userBook")
    public String userBook(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long id_user = ((User) principal).getId();
        List<BookByUserDTO> bookByUserDTO = userService.bookByUser(id_user);
        model.addAttribute("books", bookByUserDTO);
        return "user/userBook";
    }

    @PostMapping("/userBook/return")
    public String returnBook(@RequestParam(name = "id") Long id, Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long id_user = ((User) principal).getId();
        orderService.returnOrder(id_user, id);

        List<BookByUserDTO> bookByUserDTO = userService.bookByUser(id_user);
        model.addAttribute("books", bookByUserDTO);
        return "user/userBook";
    }

    @PostMapping("/userBook/pay")
    public String pay(@RequestParam(name = "id") Long id, Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long id_user = ((User) principal).getId();
        orderService.payOrder(id_user, id);


        List<BookByUserDTO> bookByUserDTO = userService.bookByUser(id_user);
        model.addAttribute("books", bookByUserDTO);
        return "user/userBook";
    }

    @GetMapping("/userOrder")
    public String userOrder(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long id_user = ((User) principal).getId();
        List<BookDTO> bookDTOS = userService.orderByUser(id_user);
        model.addAttribute("orders", bookDTOS);
        return "user/userOrder";
    }

    @PostMapping("/userOrder/decline")
    public String decline(@RequestParam(name = "id") Long id, Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long id_user = ((User) principal).getId();
        orderService.declineOrder(id_user, id);


        List<BookDTO> bookDTOS = userService.orderByUser(id_user);
        model.addAttribute("orders", bookDTOS);
        return "user/userBook";
    }

    @GetMapping("/userProfile")
    public String userProfile(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long id_user = ((User) principal).getId();

        UserForProfileDTO userForProfileDTO = userService.infoForProfile(id_user);
        model.addAttribute("user", userForProfileDTO);
        return "user/userProfile";
    }


    @PostMapping("/userProfile/edit")
    public String edit(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long id_user = ((User) principal).getId();


        UserForProfileDTO userForProfileDTO = userService.infoForProfile(id_user);
        model.addAttribute("tab", true);
        model.addAttribute("userForm", userForProfileDTO);
        model.addAttribute("user", userForProfileDTO);
        return "user/userProfile";
    }

    @PostMapping("/userProfile/update")
    public String update(@ModelAttribute("userForm") @Valid UserForProfileDTO userForm, Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long id_user = ((User) principal).getId();

        if (!userService.editProfile(id_user, userForm)) {
            model.addAttribute("wrongData", true);
        }

        logger.info(userForm);
        UserForProfileDTO userForProfileDTO = userService.infoForProfile(id_user);
        model.addAttribute("user", userForProfileDTO);
        return "user/userProfile";
    }
}
