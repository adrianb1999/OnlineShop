package com.adrian99.onlineShop.dto;

import com.adrian99.onlineShop.model.Address;
import com.adrian99.onlineShop.model.OrderStatus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

//Experimental
public class OrderDTO {
    private Long id;
    private Address shippingAddress;
    private Address billingAddress;
    private String phoneNumber;
    private OrderStatus orderStatus;
    private BigDecimal value;
    private BigDecimal shippingFee;
    private List<OrderProductDTO> orderProductDTOList = new ArrayList<>();

    public OrderDTO(Long id, Address shippingAddress, Address billingAddress, String phoneNumber, OrderStatus orderStatus, BigDecimal value, BigDecimal shippingFee, List<OrderProductDTO> orderProductDTOList) {
        this.id = id;
        this.shippingAddress = shippingAddress;
        this.billingAddress = billingAddress;
        this.phoneNumber = phoneNumber;
        this.orderStatus = orderStatus;
        this.value = value;
        this.shippingFee = shippingFee;
        this.orderProductDTOList = orderProductDTOList;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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
}
