package com.adrian99.onlineShop.repository.custom;

import java.util.*;

public interface OrderCustomRepository {
    Map<String, Object> findOrderById(Long id);
    List<Map<String, Object>> findAllOrders();
}
