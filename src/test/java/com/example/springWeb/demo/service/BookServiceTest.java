package com.example.springWeb.demo.service;

import com.example.springWeb.demo.dto.BookDTO;
import com.example.springWeb.demo.model.Book;
import com.example.springWeb.demo.model.Details;
import com.example.springWeb.demo.model.Order;
import com.example.springWeb.demo.model.User;
import com.example.springWeb.demo.repository.BookRepository;
import com.example.springWeb.demo.repository.OrderRepository;
import com.example.springWeb.demo.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@SpringBootTest
public class BookServiceTest {

    @Autowired
    private BookService bookService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private OrderRepository orderRepository;


    @Test
    public void testFindByAuthorOrName() {
        String text = "Д";

        when(bookRepository.findAllByActiveTrue()).thenReturn(createBooks());

        List<BookDTO> bookDTOS = bookService.findByAuthorOrName(text);

        assertEquals(1, bookDTOS.size());

        assertEquals("Дети капитана Гранта", bookDTOS.get(0).getName());

    }

    @Test
    public void testCheckDebtInBookByUser() {
        long id = 1;

        when(orderRepository.findAllByUser_idAndStatusIsTrue(id)).thenReturn(createOrders());

        List<Order> list = bookService.checkDebtInBookByUser(id);

        assertEquals(300, list.get(0).getDebt());

        assertEquals(0, list.get(1).getDebt());

    }

    @Test
    public void testSortBookByName() {
        String word = "sortName";

        when(bookRepository.findByOrderByNameAsc()).thenReturn(createBooks());

        List<BookDTO> bookDTOS = bookService.sort(word);

        assertEquals("Дети капитана Гранта", bookDTOS.get(0).getName());

    }

    @Test
    public void testSortBookByAuthor() {
        String word = "sortAuthor";

        when(bookRepository.findAllByActiveTrue()).thenReturn(createBooks());

        List<BookDTO> bookDTOS = bookService.sort(word);

        assertEquals("Мастер Шеф", bookDTOS.get(1).getAuthor());

    }

    @Test
    public void testSortBookByPublisher() {
        String word = "sortPublisher";

        when(bookRepository.findAllByActiveTrue()).thenReturn(createBooks());

        List<BookDTO> bookDTOS = bookService.sort(word);

        assertEquals("ЗНАК", bookDTOS.get(0).getPublisher());

    }

    @Test
    public void testSortBookByPublisherDate() {
        String word = "sortPublisherDate";

        when(bookRepository.findAllByActiveTrue()).thenReturn(createBooks());

        List<BookDTO> bookDTOS = bookService.sort(word);

        assertEquals("2000", bookDTOS.get(0).getPublisherDate());

    }


    private List<Order> createOrders() {

        Order order1 = Order
                .builder()
                .id(1)
                .user(createUser(1, true, "v@gmail.com", "Vasya", "12345434fwe"))
                .book(createBook(1,"Дети капитана Гранта", false,createDetails(1,"Жуль Верн",
                        "ИНИГМА", "2015", "Adventure", "Nice book", 1000)))
                .status(true)
                .date(Date.valueOf("2021-07-07"))
                .debt(0)
                .build();

        Order order2 = Order
                .builder()
                .id(1)
                .user(createUser(1, true, "v@gmail.com", "Vasya", "12345434fwe"))
                .book(createBook(1,"Дети капитана Гранта", false,createDetails(1,"ИНИГМА",
                        "ИНИГМА", "2015", "Adventure", "Nice book", 500)))
                .status(true)
                .date(Date.valueOf(LocalDate.now().plusDays(31)))
                .debt(0)
                .build();


        return List.of(order1, order2);
    }

    private Order createOrder(long id, User user, Book book,
                              Date returnDate, int debt, boolean status) {
        return Order
                .builder()
                .id(id)
                .user(user)
                .book(book)
                .date(returnDate)
                .status(status)
                .debt(debt)
                .build();
    }

    private User createUser(long id, boolean active, String email, String name, String password) {
        return User
                .builder()
                .id(id)
                .active(active)
                .email(email)
                .name(name)
                .password(password)
                .build();
    }

    private List<Book> createBooks() {

        Book book1= createBook(1,"Дети капитана Гранта", true,createDetails(1,"Жуль Верн",
                "ИНИГМА", "2015", "Adventure", "Nice book", 500));


        Book book2 = createBook(2,"Кухня",true,createDetails(2,"Мастер Шеф",
                "ЗНАК", "2000", "Кулинария", "Топ 10 рецептов", 100));


        return List.of(book1, book2);
    }

    private Book createBook(long id, String name, boolean active, Details details) {
        return Book
                .builder()
                .id(id)
                .name(name)
                .active(active)
                .details(details)
                .build();
    }
    private Details createDetails(long id, String author, String publisher,
                                  String publisherDate,String genre, String description, int price) {
        return Details
                .builder()
                .id(id)
                .author(author)
                .publisher(publisher)
                .publisherDate(publisherDate)
                .genre(genre)
                .description(description)
                .price(price)
                .build();
    }
}