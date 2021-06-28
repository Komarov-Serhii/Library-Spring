package com.example.springWeb.demo.service;

import com.example.springWeb.demo.dto.*;
import com.example.springWeb.demo.model.Book;
import org.springframework.stereotype.Service;

import java.util.List;


public interface BookService {

    Book saveBook(Book book);

    boolean deleteBook(final long bookId);

    List<BookDTO> getAllBooks();

    List<BookDTO> getAllBooksByFree();

    List<BookDTO> findByAuthorOrName(String text);

    List<BookDTO> sort(String word);



    Book getBookById(final long bookId);

//    Book findByName(String name);
//
//    Optional<Book> findByEmail(String email);
//
//    Book getByLoginAndPass(String name,String password);

}
