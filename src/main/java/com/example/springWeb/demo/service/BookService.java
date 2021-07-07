package com.example.springWeb.demo.service;

import com.example.springWeb.demo.dto.BookDTO;
import com.example.springWeb.demo.model.Book;
import com.example.springWeb.demo.model.Order;
import com.example.springWeb.demo.model.User;
import com.example.springWeb.demo.repository.BookRepository;
import com.example.springWeb.demo.repository.OrderRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    OrderRepository orderRepository;

    private Logger logger = Logger.getLogger(BookService.class);


    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public boolean deleteBook(long bookId) {
        bookRepository.deleteById(bookId);
        return true;
    }


    public List<BookDTO> getAllBooks() {
        List<Book> bookList = bookRepository.findAll();
        return parsingBookInBookDTO(bookList);
    }


    public List<BookDTO> getAllBooksByFree() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long id_user = ((User)principal).getId();
        List<Book> bookList = bookRepository.findAllByActiveTrue();
        checkDebtInBookByUser(id_user);
        return parsingBookInBookDTO(bookList);
    }


    public Book getBookById(long bookId) {
        return bookRepository.findById(bookId).orElseThrow(() -> new EntityNotFoundException("Book not found"));
    }


    public List<BookDTO> findByAuthorOrName(String text) {
        List<BookDTO> bookDTO = parsingBookInBookDTO(bookRepository.findAllByActiveTrue());
        return bookDTO.stream()
                .filter(o -> o.getName().toUpperCase().contains(text.toUpperCase())
                        || o.getAuthor().toUpperCase().contains(text.toUpperCase()))
                .collect(Collectors.toList());
    }


    public List<BookDTO> sort(String word) {
        if (word.equals("sortName")) {
            return sortByName();
        }
        else if (word.equals("sortAuthor")) {
            return sortByAuthor();
        }

        else if (word.equals("sortPublisher")) {
            return sortPublisher();
        } else {
            return sortPublisherDate();
        }

    }

    private List<BookDTO> sortByName(){
        List<Book> books = bookRepository.findByOrderByNameDesc();
        return parsingBookInBookDTO(books);
    }

    private List<BookDTO> sortByAuthor() {
        List<BookDTO> list = getAllBooksByFree();
        list.sort(new BookDTO.AuthorComparator());
        return list;
    }

    private List<BookDTO> sortPublisher() {
        List<BookDTO> list = getAllBooksByFree();
        list.sort(new BookDTO.AuthorComparator());
        return list;
    }

    private List<BookDTO> sortPublisherDate() {
        List<BookDTO> list = getAllBooksByFree();
        list.sort(new BookDTO.AuthorComparator());
        return list;
    }

//    public List<BookDTO> listBookByUser(Long id) {
//        List<Order> order = orderRepository.findAllByUser(id);
//
//        return null;
//    }


    public void checkDebtInBookByUser(long id) {
        List<Order> orders = orderRepository.findAllByUser_idAndStatusIsTrue(id);
        if (!orders.isEmpty()) {
            orders.stream()
                    .filter(o -> LocalDate.now().isAfter(Instant.ofEpochMilli(o.getDate().getTime()).atZone(ZoneId.systemDefault()).toLocalDate()))
                    .forEach(o -> o.setDebt(o.getBook().getDetails().getPrice() / 100 * 30));
            for (Order order : orders) {
                orderRepository.save(order);
            }
        }
    }


    private List<BookDTO> parsingBookInBookDTO(List<Book> list) {
        List<BookDTO> bookDTOs = new ArrayList<>();
        for (Book book : list) {
            bookDTOs.add(BookDTO.builder()
                    .id(book.getId())
                    .name(book.getName())
                    .author(book.getDetails().getAuthor())
                    .publisher(book.getDetails().getPublisher())
                    .publisherDate(book.getDetails().getPublisherDate())
                    .description(book.getDetails().getDescription())
                    .price(book.getDetails().getPrice())
                    .genre(book.getDetails().getGenre())
                    .build());
        }
        return bookDTOs;
    }

}
