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
        User userFromDB = userRepository.findByEmail(user.getEmail());

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

    public List<UserDTO> getAllReaders() {
        return parsingUserInUserDTO(userRepository.getUserListByRolesName("ROLE_USER"));

    }


    public User getUserById(long personId) {
        return userRepository.findById(personId).orElseThrow(() -> new EntityNotFoundException("Person not found"));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    public InfoBookAndUserDTO countInfoAboutUserAndBook() {
        return InfoBookAndUserDTO
                .builder()
                .countUser(userRepository.findAll().size())
                .countActiveUser(userRepository.findAllByActiveTrue().size())
                .countBlockedUser(userRepository.findAllByActiveFalse().size())
                .countBook(bookRepository.findAll().size())
                .countBusyBook(bookRepository.findAllByActiveFalse().size())
                .countOrderBook(orderRepository.findAll().size())
                .build();
    }

    public InfoUserDTO countInfoUserDTO(long userId) {
        List<Order> orders = orderRepository.findAllByUser_IdAndStatusIsTrue(userId);
        int count = 0;
        count += orders.stream().mapToInt(Order::getDebt).sum();

        return InfoUserDTO.builder()
                .contBook(orders.size())
                .contOrder(orderRepository.findAllByUser_IdAndStatusIsFalse(userId).size())
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

    public UserForProfileDTO infoForProfile(long id) {
        User user = userRepository.getById(id);
        return UserForProfileDTO.builder()
                .name(user.getName())
                .email(user.getUsername())
                .build();
    }

    public boolean editProfile(long id, UserForProfileDTO userForm) {
        User user = userRepository.getById(id);


        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());

        if (passwordVerify(user, userForm.getOldPassword())) {
            user.setPassword(bCryptPasswordEncoder.encode(userForm.getNewPassword()));
        } else {
            return false;
        }

        userRepository.save(user);

        return true;
    }


    public boolean passwordVerify(User user, String pass) {
        return bCryptPasswordEncoder.matches(pass, user.getPassword());
    }



    private List<UserDTO> parsingUserInUserDTO(List<User> list) {
        List<UserDTO> userDTOS = new ArrayList<>();
        for (User user : list) {
            var userDTO = UserDTO
                    .builder()
                    .id(user.getId())
                    .name(user.getName())
                    .email(user.getUsername())
                    .active(user.isActive())
                    .build();
            userDTOS.add(userDTO);
        }
        return userDTOS;
    }

}
