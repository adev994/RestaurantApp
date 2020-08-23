package com.restaurant.management.dao;

import com.restaurant.management.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<User,Integer> {

    Optional<User> findByLogin(String login);
    User findByName(String name);
    boolean existsByLogin(String login);


}
