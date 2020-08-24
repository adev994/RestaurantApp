package com.restaurant.management.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "my_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="order_id" )
    private int id;

    @Column(name="payment_by",nullable = true,updatable = true)
    private String paymentBy;

    @Column(name = "payed")
    private boolean payed;

    @OneToMany(cascade = CascadeType.REMOVE,
    fetch = FetchType.LAZY,
    mappedBy = "orderMain")
    private List<OrderFood> orderFoods=new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "order_date",nullable = true,updatable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date order_date;

    @Column(name="selected",nullable = true,updatable = true)
    private boolean selected;

    public Order(boolean payed, User user,boolean selected) {
        this.payed = payed;
        this.user = user;
        this.selected=selected;
    }

    public Order() {
    }

    public String getPaymentBy() {
        return paymentBy;
    }

    public void setPaymentBy(String paymentBy) {
        this.paymentBy = paymentBy;
    }

    public boolean isPayed() {
        return payed;
    }

    public void setPayed(boolean payed) {
        this.payed = payed;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<OrderFood> getOrderFoods() {
        return orderFoods;
    }

    public void setOrderFoods(List<OrderFood> orderFoods) {
        this.orderFoods = orderFoods;
    }

    public Date getOrder_date() {
        return order_date;
    }

    public void setOrder_date(Date order_date) {
        this.order_date = order_date;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
