package com.example.springWeb.demo.repository;

import com.example.springWeb.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    List<User> findAllByActiveTrue();

    List<User> findAllByActiveFalse();

    List<User> getUserListByRolesName(String roleName);


}
