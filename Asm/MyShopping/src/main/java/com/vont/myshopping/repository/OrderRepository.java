package com.vont.myshopping.repository;

import com.vont.myshopping.models.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByCreateBy(String username);
}