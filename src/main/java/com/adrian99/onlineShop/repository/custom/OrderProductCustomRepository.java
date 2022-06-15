package com.adrian99.onlineShop.repository.custom;

import com.adrian99.onlineShop.model.OrderProduct;

import java.util.List;

public interface OrderProductCustomRepository {
    List<OrderProduct> findAllOrderProductByOrderId(Long id);
}
