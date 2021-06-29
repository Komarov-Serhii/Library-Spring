package com.example.springWeb.demo.repository;

import com.example.springWeb.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByName(String name);

    Optional<User> findByEmail(String email);

    User findByEmailAndPassword(String name, String password);

}
