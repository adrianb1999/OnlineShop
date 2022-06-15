package com.adrian99.onlineShop.service.implementation;

import com.adrian99.onlineShop.model.OrderProduct;
import com.adrian99.onlineShop.repository.OrderProductRepository;
import com.adrian99.onlineShop.service.OrderProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderProductServiceImpl implements OrderProductService {

    private final OrderProductRepository orderProductRepository;

    public OrderProductServiceImpl(OrderProductRepository orderProductRepository) {
        this.orderProductRepository = orderProductRepository;
    }

    @Override
    public Iterable<OrderProduct> findAll() {
        return null;
    }

    @Override
    public OrderProduct findById(Long aLong) {
        return null;
    }

    @Override
    public OrderProduct save(OrderProduct object) {
        return orderProductRepository.save(object);
    }

    @Override
    public <S extends OrderProduct> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public List<OrderProduct> findAllOrderProductByOrderId(Long id) {
        return findAllOrderProductByOrderId(id);
    }
}
