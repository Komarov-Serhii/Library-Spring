package com.example.springWeb.demo.repository;

import com.example.springWeb.demo.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByActiveTrue();

    List<Book> findByOrderByNameDesc();
}
