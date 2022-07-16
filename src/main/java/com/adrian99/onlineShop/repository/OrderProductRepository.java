package com.adrian99.onlineShop.repository;

import com.adrian99.onlineShop.model.OrderProduct;
import com.adrian99.onlineShop.repository.custom.OrderCustomRepository;
import com.adrian99.onlineShop.repository.custom.OrderProductCustomRepository;
import org.springframework.data.repository.CrudRepository;

public interface OrderProductRepository extends CrudRepository<OrderProduct, Long>, OrderProductCustomRepository {
}
