package com.adrian99.onlineShop.repository;

import com.adrian99.onlineShop.model.Order;
import com.adrian99.onlineShop.repository.custom.OrderCustomRepository;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long>,
                                            OrderCustomRepository {
}
