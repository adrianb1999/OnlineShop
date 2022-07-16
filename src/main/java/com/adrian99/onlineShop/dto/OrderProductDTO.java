package com.adrian99.onlineShop.dto;

import com.adrian99.onlineShop.model.OrderProduct;

public class OrderProductDTO {
    private Long id;
    private Long orderId;
    private Long productId;
    private Integer amount;

    public OrderProductDTO(Long id, Long orderId, Long productId, Integer amount) {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.amount = amount;
    }

    public OrderProductDTO(OrderProduct orderProduct){
        this.amount = orderProduct.getAmount();
        this.productId = orderProduct.getProduct().getId();
        this.orderId = orderProduct.getOrder().getId();
    }

    public OrderProductDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

}
