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

    @Transactional
    public boolean saveOrder(Long bookId) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = ((User)principal).getId();


        Book book = bookRepository.getById(bookId);
        book.setActive(false);
        bookRepository.save(book);

        Order order = Order
                .builder()
                .user(userRepository.getById(userId))
                .book(book)
                .status(false)
                .build();
        orderRepository.save(order);
        return true;
    }


    public boolean acceptOrder(long id, long bookId) {
        Order order = orderRepository.findByUser_IdAndBook_Id(id,bookId);
        order.setStatus(true);
        order.setDate(Date.valueOf(LocalDate.now().plusDays(31)));
        orderRepository.save(order);
        return true;
    }

    public boolean rejectOrder(long id, long bookId) {
        Order order = orderRepository.findByUser_IdAndBook_Id(id,bookId);
        orderRepository.deleteById(order.getId());

        Book book = bookRepository.getById(order.getBook().getId());
        book.setActive(true);
        bookRepository.save(book);
        return true;
    }

    public boolean returnOrder(long id, long bookId) {
        Order order = orderRepository.findByUser_IdAndBook_Id(id,bookId);
        orderRepository.deleteById(order.getId());

        Book book = bookRepository.getById(order.getBook().getId());
        book.setActive(true);
        bookRepository.save(book);
        return true;
    }
    public boolean payOrder(long id, long bookId) {
        Order order = orderRepository.findByUser_IdAndBook_Id(id,bookId);
        orderRepository.deleteById(order.getId());

        Book book = bookRepository.getById(order.getBook().getId());
        book.setActive(true);
        bookRepository.save(book);
        return true;
    }

    public boolean declineOrder(long id, long bookId) {
        Order order = orderRepository.findByUser_IdAndBook_Id(id,bookId);
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
            OrderDTO orderDTO = OrderDTO
                    .builder()
                    .userId(ord.getUser().getId())
                    .userName(ord.getUser().getName())
                    .email(ord.getUser().getUsername())
                    .bookId(ord.getBook().getId())
                    .bookName(ord.getBook().getName())
                    .author(ord.getBook().getDetails().getAuthor())
                    .build();
            orderDTOS.add(orderDTO);
        }
        return orderDTOS;
    }
}
