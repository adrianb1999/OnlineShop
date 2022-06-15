package com.adrian99.onlineShop.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "Orders")
public class Order {
    @Id
    @GeneratedValue()
    private Long id;

    @OneToOne
    private Address shippingAddress;

    @OneToOne
    private Address billingAddress;

    private String phoneNumber;

    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;

    private BigDecimal value;

    @OneToOne
    private User user;

    private BigDecimal shippingFee;

    public Order(Address shippingAddress, Address billingAddress, String phoneNumber, OrderStatus orderStatus, BigDecimal value, User user, BigDecimal shippingFee) {
        this.shippingAddress = shippingAddress;
        this.billingAddress = billingAddress;
        this.phoneNumber = phoneNumber;
        this.orderStatus = orderStatus;
        this.value = value;
        this.user = user;
        this.shippingFee = shippingFee;
    }

    public Order() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public Address getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(BigDecimal shippingFee) {
        this.shippingFee = shippingFee;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
