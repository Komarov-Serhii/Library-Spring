package com.example.springWeb.demo.security;

import com.example.springWeb.demo.model.Person;
import com.example.springWeb.demo.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AccountDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private PersonRepository personRepo;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Person person = personRepo.findByEmail(login).orElseThrow(() -> new UsernameNotFoundException("User doesn't exists"));
        return SecurityAccount.fromAccount(person);
    }
}
