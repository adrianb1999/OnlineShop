package com.adrian99.onlineShop.model;

import javax.persistence.*;

@Entity
@Table(name = "ORDERS_PRODUCTS")
public class OrderProduct {
    private Long id;
    private Order order;
    private Product product;

    private int amount;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "ORDER_ID")
    public Order getOrder(){
        return order;
    }
    public void setOrder(Order order){
        this.order = order;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "PRODUCT_ID")
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
