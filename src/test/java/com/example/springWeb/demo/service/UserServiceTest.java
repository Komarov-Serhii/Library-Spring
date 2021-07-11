package com.example.springWeb.demo.service;

import com.example.springWeb.demo.dto.InfoUserDTO;
import com.example.springWeb.demo.model.*;
import com.example.springWeb.demo.repository.OrderRepository;
import com.example.springWeb.demo.repository.UserRepository;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private OrderRepository orderRepository;


    @Test
    public void testCountInfoUserDTO() {
        long id = 1;

        when(orderRepository.findAllByUser_IdAndStatusIsTrue(id)).thenReturn(createListOrdersWhereStatusTrue());
        when(orderRepository.findAllByUser_IdAndStatusIsFalse(id)).thenReturn(createListOrdersWhereStatusFalse());

        InfoUserDTO infoUserDTO = userService.countInfoUserDTO(id);

        assertEquals(300, infoUserDTO.getContDebt());
        assertEquals(2, infoUserDTO.getContBook());
        assertEquals(2, infoUserDTO.getContOrder());

        verify(orderRepository, times(1)).findAllByUser_IdAndStatusIsTrue(id);
    }


    @Test
    public void testChangeActive() {
        long id = 1;
        boolean active = true;


        when(userRepository.getById(id)).thenReturn(createUser(id, "Serg", "sergo@gmail.com", active, "124124vdsvF"));

        userService.changeActive(id, active);

        assertEquals(false, userRepository.getById(id).isActive());
    }


    private List<Order> createListOrdersWhereStatusTrue() {
        Order order = new Order();
        order.setId(1);
        order.setUser(createUser(1, "Vasya", "v@gmail.com", false, "12345434fwe"));
        order.setBook(createBook(1, "Дети капитана Гранта", true, createDetails(1, "ИНИГМА",
                "ИНИГМА", "2015", "Adventure", "Nice book", 500)));
        order.setStatus(true);
        order.setDate(Date.valueOf("2020-10-10"));
        order.setDebt(0);

        Order order2 = new Order();
        order2.setId(1);
        order2.setUser(createUser(1, "Serg", "sergo@gmail.com", false, "124fwe"));
        order2.setBook(createBook(1, "Дети капитана Гранта", true, createDetails(1, "ИНИГМА",
                "ИНИГМА", "2015", "Adventure", "Nice book", 500)));
        order2.setStatus(true);
        order2.setDate(Date.valueOf("2020-04-04"));
        order2.setDebt(300);

        return List.of(order, order2);
    }

    private List<Order> createListOrdersWhereStatusFalse() {
        Order order1 = Order
                .builder()
                .id(1)
                .user(createUser(1, "Vasya", "v@gmail.com", false, "12345434fwe"))
                .book(createBook(1, "Дети капитана Гранта", true, createDetails(1, "ИНИГМА",
                        "ИНИГМА", "2015", "Adventure", "Nice book", 500)))
                .debt(0)
                .date(null)
                .status(false)
                .build();

        Order order2 = Order
                .builder()
                .id(1)
                .user(createUser(1, "Vasya", "v@gmail.com", false, "12345434fwe"))
                .book(createBook(1, "Дети капитана Гранта", true, createDetails(1, "ИНИГМА",
                        "ИНИГМА", "2015", "Adventure", "Nice book", 500)))
                .debt(0)
                .date(null)
                .status(false)
                .build();

        return List.of(order1, order2);
    }

    private User createUser(long id, String name, String email, boolean active, String password) {
        return User
                .builder()
                .id(id)
                .active(active)
                .username(email)
                .name(name)
                .password(password)
                .build();
    }

    private Book createBook(long id, String name, boolean active, Details details) {
        return Book
                .builder()
                .id(id)
                .name(name)
                .details(details)
                .active(active)
                .build();
    }

    private Details createDetails(long id, String author, String publisher,
                                  String publisherDate, String genre, String description, int price) {
        return Details
                .builder()
                .id(id)
                .author(author)
                .publisher(publisher)
                .publisherDate(publisherDate)
                .genre(genre)
                .description(description)
                .price(price)
                .build();
    }

}