package com.example.springWeb.demo.service;

import com.example.springWeb.demo.controller.MainController;
import com.example.springWeb.demo.dto.*;
import com.example.springWeb.demo.model.Book;
import com.example.springWeb.demo.model.Order;
import com.example.springWeb.demo.model.User;
import com.example.springWeb.demo.model.Role;
import com.example.springWeb.demo.repository.BookRepository;
import com.example.springWeb.demo.repository.OrderRepository;
import com.example.springWeb.demo.repository.RoleRepository;
import com.example.springWeb.demo.repository.UserRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.sql.Date;
import java.util.*;

@Service
public class UserService implements UserDetailsService {

    private Logger logger = Logger.getLogger(MainController.class);


    @Autowired
    private UserRepository userRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;


    public boolean saveUser(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername());

        if (userFromDB != null) {
            return false;
        }

        user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(true);
        userRepository.save(user);
        return true;
    }

    public boolean changeActive(Long id, boolean active) {
        User user = userRepository.getById(id);
        logger.info(user);
        if (active) {
            user.setActive(false);
        } else {
            user.setActive(true);
        }
        userRepository.save(user);
        logger.info(user);
        return true;
    }


    public boolean deleteUser(long personId) {
        userRepository.deleteById(personId);
        return true;
    }


    public List<UserDTO> getAllUsers() {
        return parsingUserInUserDTO(userRepository.findAll());
    }

    public List<UserDTO> getAllReaders() {
        return parsingUserInUserDTO(userRepository.getUserListByRolesName("ROLE_USER"));

    }


    public User getUserById(long personId) {
        return userRepository.findById(personId).orElseThrow(() -> new EntityNotFoundException("Person not found"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    public InfoBookAndUserDTO countInfoAboutUserAndBook() {
        var infoBookAndUserDTO = new InfoBookAndUserDTO();

        infoBookAndUserDTO.setCountUser(userRepository.findAll().size());
        infoBookAndUserDTO.setCountActiveUser(userRepository.findAllByActiveTrue().size());
        infoBookAndUserDTO.setCountBlockedUser(userRepository.findAllByActiveFalse().size());
        infoBookAndUserDTO.setCountBook(bookRepository.findAll().size());
        infoBookAndUserDTO.setCountBusyBook(bookRepository.findAllByActiveFalse().size());
        infoBookAndUserDTO.setCountOrderBook(orderRepository.findAll().size());
        return infoBookAndUserDTO;
    }

    public InfoUserDTO countInfoUserDTO(long user_id) {
        List<Order> order = orderRepository.findAllByUser_IdAndStatusIsTrue(user_id);
        int count = 0;
        count += order.stream().mapToInt(Order::getDebt).sum();

        return InfoUserDTO.builder()
                .contBook(order.size())
                .contOrder(orderRepository.findAllByUser_IdAndStatusIsFalse(user_id).size())
                .contDebt(count)
                .build();
    }


    public List<BookDTO> bookByUserForAdmin(long id) {
        List<Order> order = orderRepository.findAllByUser_IdAndStatusIsTrue(id);
        Book book;
        List<BookDTO> list = new ArrayList<>();
        for (Order ord : order) {
            book = bookRepository.getById(ord.getBook().getId());
            list.add(BookDTO.builder()
                    .id(book.getId())
                    .name(book.getName())
                    .author(book.getDetails().getAuthor())
                    .publisher(book.getDetails().getPublisher())
                    .publisherDate(book.getDetails().getPublisherDate())
                    .description(book.getDetails().getDescription())
                    .price(book.getDetails().getPrice())
                    .genre(book.getDetails().getGenre())
                    .build());

        }
        logger.info(list);
        return list;
    }

    public List<BookByUserDTO> bookByUser(long id) {
        List<Order> order = orderRepository.findAllByUser_IdAndStatusIsTrue(id);
        Book book;
        List<BookByUserDTO> list = new ArrayList<>();
        for (Order ord : order) {
            book = bookRepository.getById(ord.getBook().getId());
            list.add(BookByUserDTO.builder()
                    .id(book.getId())
                    .name(book.getName())
                    .author(book.getDetails().getAuthor())
                    .publisher(book.getDetails().getPublisher())
                    .publisherDate(book.getDetails().getPublisherDate())
                    .description(book.getDetails().getDescription())
                    .genre(book.getDetails().getGenre())
                    .price(book.getDetails().getPrice())
                    .debt(ord.getDebt())
                    .returnDate(ord.getDate())
                    .build());

        }
        logger.info(list);
        return list;
    }

    public List<BookDTO> orderByUser(long id) {
        List<Order> order = orderRepository.findAllByUser_IdAndStatusIsFalse(id);
        Book book;
        List<BookDTO> list = new ArrayList<>();
        for (Order ord : order) {
            book = bookRepository.getById(ord.getBook().getId());
            list.add(BookDTO.builder()
                    .id(book.getId())
                    .name(book.getName())
                    .author(book.getDetails().getAuthor())
                    .publisher(book.getDetails().getPublisher())
                    .publisherDate(book.getDetails().getPublisherDate())
                    .description(book.getDetails().getDescription())
                    .price(book.getDetails().getPrice())
                    .genre(book.getDetails().getGenre())
                    .build());

        }
        logger.info(list);
        return list;
    }



    private List<UserDTO> parsingUserInUserDTO(List<User> list) {
        List<UserDTO> userDTOS = new ArrayList<>();
        for (User user : list) {
            var userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setName(user.getName());
            userDTO.setEmail(user.getUsername());
            userDTO.setActive(user.isActive());
            userDTOS.add(userDTO);
        }
        return userDTOS;
    }

}
