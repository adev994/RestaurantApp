package com.restaurant.management.model;

import org.hibernate.annotations.Parent;

import javax.persistence.*;

@Entity
@Table(name="order_food")
public class OrderFood {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="orderfood_id")
    private int id;

    @OneToOne
    private User user;

    @OneToOne
    private Food food;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order orderMain;

    @Column(name = "quantity_orderfood",nullable = true,updatable = true)
    private int quantity_orderfood;

    @Column(name = "food_price")
    private float price;

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public OrderFood() {

    }

    public OrderFood(User user, Food food, Order orderMain, int quantity_orderfood) {
        this.user = user;
        this.food = food;
        this.orderMain = orderMain;
        this.quantity_orderfood = quantity_orderfood;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public Order getOrderMain() {
        return orderMain;
    }

    public void setOrderMain(Order orderMain) {
        this.orderMain = orderMain;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity_orderfood() {
        return quantity_orderfood;
    }

    public void setQuantity_orderfood(int quantity_orderfood) {
        this.quantity_orderfood = quantity_orderfood;
    }
}
