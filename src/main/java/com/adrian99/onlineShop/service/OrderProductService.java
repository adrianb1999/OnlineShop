package com.adrian99.onlineShop.service;

import com.adrian99.onlineShop.model.OrderProduct;
import com.adrian99.onlineShop.repository.custom.OrderProductCustomRepository;

public interface OrderProductService extends CrudService<OrderProduct, Long>, OrderProductCustomRepository {
}
