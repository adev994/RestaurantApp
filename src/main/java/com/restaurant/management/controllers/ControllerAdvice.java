package com.restaurant.management.controllers;

import com.restaurant.management.dao.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

@org.springframework.web.bind.annotation.ControllerAdvice(basePackageClasses = FoodController.class)
public class ControllerAdvice {

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private CRUD crud;

    @ModelAttribute
    public void addAttributes(Model model,Authentication authentication) {
        if(AuthenticationSystem.isLogged()) {
            crud.Cart(authentication, model);
        }
    }

}
