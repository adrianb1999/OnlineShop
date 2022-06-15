package com.adrian99.onlineShop.service;

import com.adrian99.onlineShop.model.Order;
import com.adrian99.onlineShop.repository.custom.OrderCustomRepository;

public interface OrderService extends CrudService<Order, Long>, OrderCustomRepository {
}
