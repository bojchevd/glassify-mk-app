package com.example.glassify.repository;

import com.example.glassify.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByCreatedAtBetween(LocalDateTime from, LocalDateTime to);
}