package com.adrian99.onlineShop.dto;

import com.adrian99.onlineShop.model.*;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OrderDTO {
    private Long id;
    private Long userId;
    private AddressDTO shippingAddress;
    private AddressDTO billingAddress;
    private String email;
    private String phoneNumber;
    private OrderStatus orderStatus;
    private BigDecimal value;
    private BigDecimal shippingFee;

    @JsonProperty("products")
    private List<OrderProductDTO> orderProductDTOList = new ArrayList<>();

    public OrderDTO(Long id, Long userId, AddressDTO shippingAddress, AddressDTO billingAddress, String email, String phoneNumber, OrderStatus orderStatus, BigDecimal value, BigDecimal shippingFee, List<OrderProductDTO> orderProductDTOList) {
        this.id = id;
        this.userId = userId;
        this.shippingAddress = shippingAddress;
        this.billingAddress = billingAddress;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.orderStatus = orderStatus;
        this.value = value;
        this.shippingFee = shippingFee;
        this.orderProductDTOList = orderProductDTOList;
    }

    public OrderDTO(){
    }

    public void addOrder(Order order){
        this.id = order.getId();
        this.shippingAddress = new AddressDTO(order.getShippingAddress());
        this.billingAddress = new AddressDTO(order.getBillingAddress());
        this.phoneNumber = order.getPhoneNumber();
        this.orderStatus = order.getOrderStatus();
        this.value = order.getValue();
        this.shippingFee = order.getShippingFee();
    }

    public void addUser(User user){
        this.userId = user.getId();
        this.email = user.getEmail();
    }

    public void addProducts(List<OrderProduct> orderProductList){
        for(var product: orderProductList){
            orderProductDTOList.add(new OrderProductDTO(product));
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AddressDTO getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(AddressDTO shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public AddressDTO getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(AddressDTO billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(BigDecimal shippingFee) {
        this.shippingFee = shippingFee;
    }

    public List<OrderProductDTO> getOrderProductDTOList() {
        return orderProductDTOList;
    }

    public void setOrderProductDTOList(List<OrderProductDTO> orderProductDTOList) {
        this.orderProductDTOList = orderProductDTOList;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
