package com.example.springWeb.demo.service;

import com.example.springWeb.demo.dto.BookDTO;
import com.example.springWeb.demo.model.Book;
import com.example.springWeb.demo.model.Details;
import com.example.springWeb.demo.model.Order;
import com.example.springWeb.demo.repository.BookRepository;
import com.example.springWeb.demo.repository.OrderRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    OrderRepository orderRepository;

    private Logger logger = Logger.getLogger(BookService.class);


    public boolean deleteBook(long bookId) {
        bookRepository.deleteById(bookId);
        return true;
    }


    public List<BookDTO> getAllBooksByFree() {
        List<Book> bookList = bookRepository.findAllByActiveTrue();
        return parsingBookInBookDTO(bookList);
    }

    public List<BookDTO> getAllBooksByFreeAndCheckDebt(long id) {
        logger.info(id);
        List<Book> bookList = bookRepository.findAllByActiveTrue();
        checkDebtInBookByUser(id);
        return parsingBookInBookDTO(bookList);
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

    public boolean addBook(BookDTO bookDTO) {
        Details details = Details
                .builder()
                .author(bookDTO.getAuthor())
                .description(bookDTO.getDescription())
                .genre(bookDTO.getGenre())
                .price(bookDTO.getPrice())
                .publisher(bookDTO.getPublisher())
                .publisherDate(bookDTO.getPublisherDate())
                .build();

        Book book = Book.
                builder()
                .name(bookDTO.getName())
                .details(details)
                .active(true)
                .build();

        bookRepository.save(book);
        return true;
    }


    public boolean editBook(BookDTO bookDTO) {
        Book book = bookRepository.getById(bookDTO.getId());
        book.setName(bookDTO.getName());
        book.getDetails().setAuthor(bookDTO.getAuthor());
        book.getDetails().setDescription(bookDTO.getDescription());
        book.getDetails().setGenre(bookDTO.getGenre());
        book.getDetails().setPrice(bookDTO.getPrice());
        book.getDetails().setPublisher(bookDTO.getPublisher());
        book.getDetails().setPublisherDate(bookDTO.getPublisherDate());
        bookRepository.save(book);
        return true;
    }

    public BookDTO bookForEdit(long id) {
        Book book = bookRepository.getById(id);
        return BookDTO.builder()
                .id(book.getId())
                .name(book.getName())
                .author(book.getDetails().getAuthor())
                .publisher(book.getDetails().getPublisher())
                .publisherDate(book.getDetails().getPublisherDate())
                .description(book.getDetails().getDescription())
                .price(book.getDetails().getPrice())
                .genre(book.getDetails().getGenre())
                .build();
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
