package com.restaurant.management.controllers;

import com.restaurant.management.controllers.ErrorController;
import com.restaurant.management.controllers.FoodController;
import com.restaurant.management.dao.FoodRepository;
import com.restaurant.management.dao.OrderRepository;
import com.restaurant.management.dao.OrderfoodRepository;
import com.restaurant.management.dao.UsersRepository;
import com.restaurant.management.model.Food;
import com.restaurant.management.model.Order;
import com.restaurant.management.model.OrderFood;
import com.restaurant.management.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletContext;
import javax.websocket.server.PathParam;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class CRUD {

    //make sure to change Your custom path in the application.properties file.
    @Value("${image_path}")
    private String image_path;

    private final PasswordEncoder passwordEncoder;
    private final FoodRepository foodRepository;
    private final OrderfoodRepository orderfoodRepository;
    private final UsersRepository usersRepository;
    private final OrderRepository orderRepository;

    public CRUD(PasswordEncoder passwordEncoder, FoodRepository foodRepository, OrderfoodRepository orderfoodRepository, UsersRepository usersRepository, OrderRepository orderRepository) {
        this.passwordEncoder = passwordEncoder;
        this.foodRepository = foodRepository;
        this.orderfoodRepository = orderfoodRepository;
        this.usersRepository = usersRepository;
        this.orderRepository = orderRepository;
    }


    @PostMapping("/addfood")
    public String savefood(MultipartFile image, String name, String category, float price, int quantity, String description) throws IllegalStateException, IOException {
        Food food=new Food();
        food.setName(name);
        food.setPrice(price);
        food.setQuantity(quantity);
        food.setDescription(description);
        food.setImage(image.getOriginalFilename());
        food.setCategory(category);
        foodRepository.save(food);
        //save image to the database
        image.transferTo(new File(image_path+"/"+category+"/"+image.getOriginalFilename()));
        return "redirect:/addfood";
    }

    @GetMapping("/cart")
    public String addToCart(Authentication authentication, Model model){
        User user = usersRepository.findByName(authentication.getName());
        Order order = user.getOrders().stream().filter(myOrder -> !myOrder.isPayed() && myOrder.isSelected()).findFirst().orElse(new Order(false,user,true));
        orderRepository.save(order);
        List<OrderFood> orderFoods = order.getOrderFoods();
        model.addAttribute("total_price", FoodController.total_price(orderFoods));
        model.addAttribute("order_foods_size",Total_quantity(orderFoods));
        model.addAttribute("order_foods",orderFoods);
        return "food-Cart";
    }

    @PostMapping("/add-to-cart")
    public String add_to_cart(int food_id, Authentication authentication){
        OrderFood orderFood;

        Food food=foodRepository.findByIdFood(food_id);
        User user = usersRepository.findByName(authentication.getName());
        int quantity=food.getQuantity();
        if(quantity>=1){
            if(quantity==1){
                food.setQuantity(0);
            }
            else{
                food.setQuantity(quantity-1);
            }
            Order order1 = user.getOrders().stream().filter(order -> !order.isPayed() && order.isSelected()).findFirst().get();
            orderFood = order1.getOrderFoods().stream().filter(orderFood1 -> orderFood1.getFood().getIdFood()==food_id).findFirst().orElse(new OrderFood(user,food,order1,0));
            orderFood.setQuantity_orderfood(orderFood.getQuantity_orderfood()+1);
            orderFood.setPrice(food.getPrice());
            orderfoodRepository.save(orderFood);
        }
       foodRepository.save(food);
        return "redirect:/food";
    }

    @GetMapping("/remove_form_cart")
    public String remove_from_cart(@RequestParam(name = "id",required = true) int id, Authentication authentication){
        User user = usersRepository.findByName(authentication.getName());
        OrderFood orderFood =orderfoodRepository.findAllByUser(user).stream().filter(orderFood1 -> orderFood1.getId()==id).findFirst().get();
        Food food=foodRepository.findByIdFood(orderFood.getFood().getIdFood());
        orderfoodRepository.delete(orderFood);
        food.setQuantity(food.getQuantity()+1);
        foodRepository.save(food);
        return "redirect:/cart";
    }

    @PostMapping("/checkout")
    public String checkout(String PaymentBy,Authentication authentication){
        User user = usersRepository.findByName(authentication.getName());
        Order order=orderRepository.findAllByUser(user).stream().filter(order1 -> !order1.isPayed() && order1.isSelected()).findFirst().get();
        order.setPayed(true);
        order.setPaymentBy(PaymentBy);
        order.setOrder_date(new Date());
        orderRepository.save(order);
        return "redirect:/checkout";
    }

    @GetMapping("/delete-order")
    public String delete_order(@RequestParam(name = "id",required = true)int id, Authentication authentication){
        User user = usersRepository.findByName(authentication.getName());
        Order order =orderRepository.findAllByUser(user).stream().filter(order1 -> order1.getId()==id).findFirst().get();
        orderRepository.delete(order);
        return "redirect:/orders";
    }

    @GetMapping("/update-food")
    public String update_food(@RequestParam(name = "id",required = false)int food_id,Model model,Authentication authentication){
        if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))){
            Food food = foodRepository.findByIdFood(food_id);
            model.addAttribute("food",food);
            return "update-food";
        }
        else{
            return "redirect:/food";
        }

    }

    @PostMapping("/update-food")
    public String save_food(Food food, RedirectAttributes redirectAttributes){
        Food food1 = foodRepository.findById(food.getIdFood()).get();
        food1.setName(food.getName());
        food1.setCategory(food.getCategory());
        food1.setDescription(food.getDescription());
        food1.setPrice(food.getPrice());
        food1.setQuantity(food.getQuantity());
        foodRepository.save(food1);
        redirectAttributes.addFlashAttribute("update_food_message","Your food has been updated successfully ! ");
        return "redirect:/food";
    }
    public int Total_quantity(List<OrderFood> orderFoods){

        List<Integer> integers = orderFoods.stream().map(orderFood -> orderFood.getQuantity_orderfood()).collect(Collectors.toList());
        Integer sum = integers.stream().mapToInt(Integer::intValue).sum();
        return sum;
    }
}
