package com.example.springWeb.demo.service;

import com.example.springWeb.demo.dto.OrderDTO;
import com.example.springWeb.demo.model.Order;
import com.example.springWeb.demo.repository.BookRepository;
import com.example.springWeb.demo.repository.OrderRepository;
import com.example.springWeb.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    OrderRepository orderRepository;

    public List<OrderDTO> getAllOrder() {
        return parsingOrder(orderRepository.findAll());
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
