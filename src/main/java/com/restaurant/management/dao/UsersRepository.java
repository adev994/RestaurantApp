package com.restaurant.management.dao;

import com.restaurant.management.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends CrudRepository<User,Integer> {

    @Query("select t from User t where t.login= ?1")
    Optional<User> findByLogin(String login);
    User findByName(String name);
    boolean existsByLogin(String login);
}
