package com.example.springWeb.demo.repository;

import com.example.springWeb.demo.model.Role;
import com.example.springWeb.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    List<User> findAllByActiveTrue();

    List<User> findAllByActiveFalse();

//    List<User> findByRolesIn(@Param("names")Collection<Long> names);

    List<User> getUserListByRolesName(String roleName);

//    List<User> findAllByRolesROLE_USER();



}
