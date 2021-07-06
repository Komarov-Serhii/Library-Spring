package com.example.springWeb.demo.service;

import com.example.springWeb.demo.dto.BookDTO;
import com.example.springWeb.demo.model.Book;
import com.example.springWeb.demo.model.Order;
import com.example.springWeb.demo.repository.BookRepository;
import com.example.springWeb.demo.repository.OrderRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
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
        List<Book> bookList = bookRepository.findAllByActiveTrue();
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

    private List<BookDTO> parsingBookInBookDTO(List<Book> list) {
        List<BookDTO> bookDTOs = new ArrayList<>();
        for (Book book : list) {
            var bookDTO = new BookDTO();
            bookDTO.setId(book.getId());
            bookDTO.setName(book.getName());
            bookDTO.setAuthor(book.getDetails().getAuthor());
            bookDTO.setPublisher(book.getDetails().getPublisher());
            bookDTO.setPublisherDate(book.getDetails().getPublisherDate());
            bookDTO.setDescription(book.getDetails().getDescription());
            bookDTO.setPrice(book.getDetails().getPrice());
            bookDTO.setGenre(book.getDetails().getGenre());
            bookDTOs.add(bookDTO);
        }
        return bookDTOs;
    }

}
