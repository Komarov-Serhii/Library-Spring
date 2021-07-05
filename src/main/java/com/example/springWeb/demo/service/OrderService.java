package com.example.springWeb.demo.service;

import com.example.springWeb.demo.controller.UserController;
import com.example.springWeb.demo.dto.OrderDTO;
import com.example.springWeb.demo.model.Book;
import com.example.springWeb.demo.model.Order;
import com.example.springWeb.demo.model.Role;
import com.example.springWeb.demo.model.User;
import com.example.springWeb.demo.repository.BookRepository;
import com.example.springWeb.demo.repository.OrderRepository;
import com.example.springWeb.demo.repository.UserRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    OrderRepository orderRepository;

    private Logger logger = Logger.getLogger(OrderService.class);

    public List<OrderDTO> getAllOrder() {
        return parsingOrder(orderRepository.findAll());
    }

    @Transactional
    public boolean saveOrder(Long id_book) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long id_user = ((User)principal).getId();

        Order order = new Order();
        Book book = bookRepository.getById(id_book);
        book.setActive(false);
        bookRepository.save(book);

        order.setUser(userRepository.getById(id_user));
        order.setBook(book);
        order.setStatus(false);

        orderRepository.save(order);
        return true;
    }

    private List<OrderDTO> parsingOrder(List<Order> order) {
        List<OrderDTO> orderDTOS = new ArrayList<>();
        for (Order ord : order) {
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setId_user(ord.getUser().getId());
            orderDTO.setName_user(ord.getUser().getName());
            orderDTO.setEmail(ord.getUser().getUsername());
            orderDTO.setId_book(ord.getBook().getId());
            orderDTO.setName_book(ord.getBook().getName());
            orderDTO.setAuthor(ord.getBook().getDetails().getAuthor());
            orderDTOS.add(orderDTO);
        }
        return orderDTOS;
    }
}
