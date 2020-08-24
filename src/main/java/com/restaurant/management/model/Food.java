package com.restaurant.management.model;

import javax.persistence.*;

@Entity
@Table(name = "food")
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_id")
    private int idFood;

    @Column(name="name",nullable = false,updatable = true)
    private String name;

    @Column(name="price",nullable = false,updatable = true)
    private float price;

    @Column(name="image",nullable = true,updatable = true)
    private String image;

    @Column(name = "quantity",nullable = true,updatable = true)
    private int quantity;

    @Column(name="description",nullable = true,updatable = true)
    private String description;

    @Column(name="category",nullable = false,updatable = true)
    private String category;


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getIdFood() {
        return idFood;
    }

    public void setIdFood(int idFood) {
        this.idFood = idFood;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
