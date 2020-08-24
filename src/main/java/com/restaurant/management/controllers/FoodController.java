package com.restaurant.management.controllers;

import com.restaurant.management.dao.FoodRepository;
import com.restaurant.management.dao.OrderRepository;
import com.restaurant.management.dao.OrderfoodRepository;
import com.restaurant.management.dao.UsersRepository;
import com.restaurant.management.model.Food;
import com.restaurant.management.model.Order;
import com.restaurant.management.model.OrderFood;
import com.restaurant.management.model.User;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.model.IModel;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class FoodController {

    private final PasswordEncoder passwordEncoder;
    private final FoodRepository foodRepository;
    private final OrderfoodRepository orderfoodRepository;
    private final UsersRepository usersRepository;

    private final OrderRepository orderRepository;

    private final ServletContext servletContext;

    public FoodController(PasswordEncoder passwordEncoder, FoodRepository foodRepository, OrderfoodRepository orderfoodRepository, UsersRepository usersRepository, OrderRepository orderRepository, ServletContext servletContext) {
        this.passwordEncoder = passwordEncoder;
        this.foodRepository = foodRepository;
        this.orderfoodRepository = orderfoodRepository;
        this.usersRepository = usersRepository;
        this.orderRepository = orderRepository;
        this.servletContext = servletContext;
    }

    // this is the home page
    @GetMapping("/")
    public String home(){
    return "home";
    }

    @GetMapping("/food")
    public String AllFood(HttpServletRequest request,Model model) throws IllegalArgumentException{


        //for pagination
        int page = 0; //default page number is 0

        if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
            page = Integer.parseInt(request.getParameter("page")) -1;
        }


        model.addAttribute("foods", foodRepository.findAll(PageRequest.of(page, 6)));

        return "food";
    }

    @GetMapping("/addfood")
    public String addFood(Model model){
        return "addfood";
    }





    @GetMapping("/food/{category}")
    public String food_category(@PathVariable(name="category") String category,Model model,HttpServletRequest request,Authentication authentication){

        //for pagination
        int page = 0; //default page number is 0

        if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
            page = Integer.parseInt(request.getParameter("page")) -1;
        }

        model.addAttribute("foods", foodRepository.findAllByCategory(category,PageRequest.of(page, 6)));

        return "food";

    }

    @GetMapping("/checkout")
    public String food_checkout(Authentication authentication,Model model){
        Order order;
        User user = usersRepository.findByName(authentication.getName());
        order = user.getOrders().stream().filter(myOrder -> !myOrder.isPayed() && myOrder.isSelected()).findFirst().orElse(new Order(false,user,true));
        List<OrderFood> orderFoods = order.getOrderFoods();
        if(orderFoods.isEmpty()){
            return "redirect:/cart";
        }
        model.addAttribute("total_price",total_price(orderFoods));
        return "food-checkout";
    }

    @GetMapping("/orders")
    public String orders(Authentication authentication,Model model){

        User user = usersRepository.findByName(authentication.getName());
        List<Order> orders = orderRepository.findAllByUser(user);
        List<Order> orders1 = orders.stream().filter(order -> order.isPayed()).collect(Collectors.toList());
        if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))){
            orders1=orderRepository.findAll().stream().filter(order -> order.isPayed()).collect(Collectors.toList());
        }
        List<Float> prices=orders1.stream().map(order -> total_price(order.getOrderFoods())).collect(Collectors.toList());

        model.addAttribute("prices",prices);
        model.addAttribute("orders",orders1);
        return "orders";
    }


    public static float total_price(List<OrderFood> orderFoods){
        float total=0;
        for (OrderFood orderFood:orderFoods){
            total+=orderFood.getPrice()*orderFood.getQuantity_orderfood();
        }
        return total;
    }

    @GetMapping("/active-orders")
    public String active_orders(Authentication authentication,Model model){
  // to do
        User user = usersRepository.findByName(authentication.getName());
        List<Order> orders = orderRepository.findAllByUser(user);
        List<Order> orders1 = orders.stream().filter(order -> !order.isPayed()).collect(Collectors.toList());
        model.addAttribute("orders",orders1);
        return "active_orders";
    }

    @GetMapping("/add-new-order")
    public String add_new_order(Authentication authentication,Model model){
        // to do
        User user = usersRepository.findByName(authentication.getName());
        Order order = new Order(false, user,true);
        orderRepository.save(order);
        return "redirect:/active-orders";
    }

    @GetMapping("choose-order")
    public String choose_order(@RequestParam(name ="id",required = true) int order_id,Authentication authentication){
        User user = usersRepository.findByName(authentication.getName());
        List<Order> orders = orderRepository.findAllByUser(user);
        Order orders1 = orders.stream().filter(order -> !order.isPayed()&& order.isSelected()).findFirst().get();
        orders1.setSelected(false);

        Order order = orderRepository.findById(order_id).get();
        order.setSelected(true);
        orderRepository.save(order);
        return "redirect:/active-orders";
    }


}
