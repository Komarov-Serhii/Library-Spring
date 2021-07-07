package com.example.springWeb.demo.repository;

import com.example.springWeb.demo.model.Book;
import com.example.springWeb.demo.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByUser_IdAndStatusIsTrue(Long id);

    List<Order> findAllByUser_IdAndStatusIsFalse(Long id);

    Optional<Order> findByBook_id(long id);

    List<Order> findAllByUser_idAndStatusIsTrue(long id);

    Order findByUser_IdAndBook_Id(long user_id, long book_id);

    List<Order> findAllByStatusIsFalse();

}
