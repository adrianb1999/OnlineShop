package com.adrian99.onlineShop.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "Products")
public class Product {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    private String name;
    private BigDecimal price;
    private Integer amount;

    @Enumerated(value = EnumType.STRING)
    private Category category;

    private String description;

    private String brand;

    private String imageUrl;

    public Product() {
    }

    public Product(String name, BigDecimal price, Integer amount, Category category, String description, String brand, String imageUrl) {
        this.name = name;
        this.price = price;
        this.amount = amount;
        this.category = category;
        this.description = description;
        this.brand = brand;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
