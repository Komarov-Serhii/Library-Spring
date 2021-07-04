package com.example.springWeb.demo.repository;

import com.example.springWeb.demo.model.Role;
import com.example.springWeb.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
