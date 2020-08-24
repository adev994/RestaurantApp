package com.restaurant.management.controllers;

import com.restaurant.management.dao.UsersRepository;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

@org.springframework.web.bind.annotation.ControllerAdvice(basePackageClasses = FoodController.class)
public class ControllerAdvice {

    private final UsersRepository usersRepository;
    private final CRUD crud;

    public ControllerAdvice(UsersRepository usersRepository, CRUD crud) {
        this.usersRepository = usersRepository;
        this.crud = crud;
    }

    @ModelAttribute
    public void addAttributes(Model model,Authentication authentication) {
        if(AuthenticationSystem.isLogged()) {
//            crud.addToCart(authentication, model);
        }
    }

}
