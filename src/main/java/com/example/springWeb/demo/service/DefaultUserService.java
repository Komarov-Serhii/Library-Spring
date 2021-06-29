package com.example.springWeb.demo.service;

import com.example.springWeb.demo.controller.MainController;
import com.example.springWeb.demo.model.User;
import com.example.springWeb.demo.model.Role;
import com.example.springWeb.demo.repository.BookRepository;
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
public class DefaultUserService implements UserDetailsService {

    private Logger logger = Logger.getLogger(MainController.class);


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;


    public boolean saveUser(User user) {
        User userFromDB = userRepository.findByName(user.getUsername());

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

    public User findByName(String name) {
        return userRepository.findByName(name);
    }


    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    public User getByLoginAndPass(String name, String password) {
        return userRepository.findByEmailAndPassword(name, password);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByName(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }
}
