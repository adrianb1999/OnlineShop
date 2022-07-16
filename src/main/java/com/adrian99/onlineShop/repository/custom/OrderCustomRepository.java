package com.adrian99.onlineShop.repository.custom;

import com.adrian99.onlineShop.dto.OrderDTO;

import java.util.*;

public interface OrderCustomRepository {
    OrderDTO findOrderById(Long id);
    List<OrderDTO> findAllOrders();

}
