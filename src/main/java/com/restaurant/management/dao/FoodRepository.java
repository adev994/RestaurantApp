package com.restaurant.management.dao;

import com.restaurant.management.model.Food;
import com.restaurant.management.model.OrderFood;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface FoodRepository extends PagingAndSortingRepository<Food,Integer> {

    Page<Food> findAllByCategory(String category,Pageable pageable);
    Food findByIdFood(int id);



}
