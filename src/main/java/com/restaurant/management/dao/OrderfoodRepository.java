package com.restaurant.management.dao;

import com.restaurant.management.model.Food;
import com.restaurant.management.model.Order;
import com.restaurant.management.model.OrderFood;
import com.restaurant.management.model.User;

import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface OrderfoodRepository extends JpaRepository<OrderFood,Integer> {

    List<OrderFood> findAllByUser(User user);
    boolean existsByOrderMain(Order order);
    OrderFood findByFood(Food food);
    boolean existsByFood(Food food);


}
