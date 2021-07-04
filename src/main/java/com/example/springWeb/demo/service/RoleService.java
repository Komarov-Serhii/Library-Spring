package com.example.springWeb.demo.service;

import com.example.springWeb.demo.model.Role;
import com.example.springWeb.demo.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    RoleRepository roleRepository;



}
