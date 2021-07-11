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


        when(userRepository.getById(id)).thenReturn(createUser(id,"Serg", "sergo@gmail.com",active));

        userService.changeActive(id, active);

        assertEquals(false, userRepository.getById(id).isActive());
    }

//    public boolean changeActive(Long id, boolean active) {
//        User user = userRepository.getById(id);
//        logger.info(user);
//        if (active) {
//            user.setActive(false);
//        } else {
//            user.setActive(true);
//        }
//        userRepository.save(user);
//        logger.info(user);
//        return true;
//    }



    private List<Order> createListOrdersWhereStatusTrue() {
        Order order = new Order();
        order.setId(1);
        order.setUser(createUser(1,"Vasya", "v@gmail.com",false));
        order.setBook(createBook());
        order.setStatus(true);
        order.setDate(Date.valueOf("2020-10-10"));
        order.setDebt(0);

        Order order2 = new Order();
        order2.setId(1);
        order2.setUser(createUser(1,"Serg", "sergo@gmail.com",false));
        order2.setBook(createBook());
        order2.setStatus(true);
        order2.setDate(Date.valueOf("2020-04-04"));
        order2.setDebt(300);

        return List.of(order,order2);
    }

    private List<Order> createListOrdersWhereStatusFalse() {
        Order order = new Order();
        order.setId(1);
        order.setUser(createUser(1,"Vasya", "v@gmail.com",false));
        order.setBook(createBook());
        order.setStatus(false);
        order.setDate(null);
        order.setDebt(0);

        Order order2 = new Order();
        order2.setId(1);
        order2.setUser(createUser(1,"Serg", "sergo@gmail.com",false));
        order2.setBook(createBook());
        order2.setStatus(false);
        order2.setDate(null);
        order2.setDebt(0);

        return List.of(order,order2);
    }

    private User createUser(long id, String name, String email, boolean active) {
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setUsername(email);
        user.setActive(active);
        user.setPassword("asdfg");
        return user;
    }
    private Book createBook() {
        Book book = new Book();
        book.setId(1);
        book.setName("Serg");
        book.setActive(true);
        book.setDetails(createDetails());
        return book;
    }
    private Details createDetails() {
        Details details = new Details();
        details.setId(1);
        details.setAuthor("Serg");
        details.setPublisher("Gener");
        details.setPublisherDate("2016");
        details.setGenre("Action");
        details.setDescription("true");
        return details;
    }

}