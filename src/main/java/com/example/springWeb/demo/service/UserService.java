package com.example.springWeb.demo.service;

import com.example.springWeb.demo.controller.MainController;
import com.example.springWeb.demo.dto.InfoBookAndUserDTO;
import com.example.springWeb.demo.model.User;
import com.example.springWeb.demo.model.Role;
import com.example.springWeb.demo.repository.BookRepository;
import com.example.springWeb.demo.repository.OrderRepository;
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



    public boolean deleteUser(long personId) {
        userRepository.deleteById(personId);
        return true;
    }


    public List<User> getAllUsers() {
        List<User> userList = userRepository.findAll();
        return new ArrayList<>(userList);
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
}
