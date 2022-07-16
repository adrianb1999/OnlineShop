package com.adrian99.onlineShop.repository.custom;

import com.adrian99.onlineShop.model.OrderProduct;

import java.util.List;
import java.util.Map;

public interface OrderProductCustomRepository {
    List<OrderProduct> findAllOrderProductByOrderId(Long id);
    Map<Long, OrderProduct> findAllOrderProductByOrderIdMap(Long id);
}
