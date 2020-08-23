package com.restaurant.management.dao;

import com.restaurant.management.model.Order;
import com.restaurant.management.model.OrderFood;
import com.restaurant.management.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Integer> {
    List<Order> findAllByUser(User user);
}
