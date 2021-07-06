package com.example.springWeb.demo.service;

import com.example.springWeb.demo.dto.OrderDTO;
import com.example.springWeb.demo.model.Book;
import com.example.springWeb.demo.model.Order;
import com.example.springWeb.demo.model.User;
import com.example.springWeb.demo.repository.BookRepository;
import com.example.springWeb.demo.repository.OrderRepository;
import com.example.springWeb.demo.repository.UserRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        return parsingOrder(orderRepository.findAllByStatusIsFalse());
    }

    public boolean deleteOrder(long id) {
        orderRepository.deleteById(id);
        return true;
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

    public Order getOrderById(long id) {
        return orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Order not found"));
    }

    public Optional<Order> getOrderByBookId(long id) {
        return Optional.ofNullable(orderRepository.findByBook_id(id).orElseThrow(() -> new EntityNotFoundException("Order not found")));
    }

//    public Order getOrderByUser(long id) {
//        return orderRepository.findByUser_id(id);
//    }

    public boolean acceptOrder(long id, long book_id) {
        Order order = orderRepository.findByUser_IdAndBook_Id(id,book_id);
        logger.info(order);
        order.setStatus(true);
        order.setDate(Date.valueOf(LocalDate.now().plusDays(31)));
        logger.info(order);
        orderRepository.save(order);
        return true;
    }

    public boolean rejectOrder(long id, long book_id) {
        Order order = orderRepository.findByUser_IdAndBook_Id(id,book_id);
        orderRepository.deleteById(order.getId());
        Book book = bookRepository.getById(order.getBook().getId());
        book.setActive(true);
        bookRepository.save(book);
        return true;
    }

    public boolean returnOrder(long id, long book_id) {
        Order order = orderRepository.findByUser_IdAndBook_Id(id,book_id);
        orderRepository.deleteById(order.getId());
        Book book = bookRepository.getById(order.getBook().getId());
        book.setActive(true);
        bookRepository.save(book);
        return true;
    }
    public boolean payOrder(long id, long book_id) {
        Order order = orderRepository.findByUser_IdAndBook_Id(id,book_id);
        orderRepository.deleteById(order.getId());
        Book book = bookRepository.getById(order.getBook().getId());
        book.setActive(true);
        bookRepository.save(book);
        return true;
    }

    public boolean declineOrder(long id, long book_id) {
        Order order = orderRepository.findByUser_IdAndBook_Id(id,book_id);
        orderRepository.deleteById(order.getId());
        Book book = bookRepository.getById(order.getBook().getId());
        book.setActive(true);
        bookRepository.save(book);
        return true;
    }



    private List<OrderDTO> parsingOrder(List<Order> order) {
        logger.info(order.size());
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
