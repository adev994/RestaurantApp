package com.restaurant.management.controllers;

import com.restaurant.management.dao.UsersRepository;
import com.restaurant.management.model.Order;
import com.restaurant.management.model.User;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@Controller
public class UserController {

    final PasswordEncoder passwordEncoder;

    private final UsersRepository userRepository;

    public UserController(PasswordEncoder passwordEncoder, UsersRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }


    @GetMapping("/login")
    public String login(){
        if (!AuthenticationSystem.isLogged()) return "user-login";
        return "redirect:/";
    }

    @GetMapping("add-user")
    public String add_user(){
        return "add-user";
    }

    @PostMapping("add-user")
    public String save_user(String name, String login, String password, RedirectAttributes redirectAttributes){
        if(!userRepository.existsByLogin(login)) {

            User user = new User();
            user.setName(name);
            user.setLogin(login.trim());
            user.setPassword(passwordEncoder.encode(password));
            user.setRoles("ROLE_WAITER");
            user.setEnable(true);
            user.setOrders(new ArrayList<Order>());
            userRepository.save(user);
            redirectAttributes.addFlashAttribute("messageAddUser","Your Waiter has been added successfully");
        }
        else{
            redirectAttributes.addFlashAttribute("messageAddUser","This login already exists , please Try again !");
        }
            return "redirect:/add-user";

    }

    @GetMapping("/about-us")
    public String about_us(){


        return "about-us";
    }


}
