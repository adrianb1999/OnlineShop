package com.adrian99.onlineShop.service.implementation;

import com.adrian99.onlineShop.model.Order;
import com.adrian99.onlineShop.repository.OrderRepository;
import com.adrian99.onlineShop.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Iterable<Order> findAll() {
        return null;
    }

    @Override
    public Order findById(Long aLong) {
        return orderRepository.findById(aLong).orElse(null);
    }

    @Override
    public Order save(Order object) {
        return orderRepository.save(object);
    }

    @Override
    public <S extends Order> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public Map<String, Object> findOrderById(Long id) {
        return orderRepository.findOrderById(id);
    }

    @Override
    public List<Map<String, Object>> findAllOrders() {
        return orderRepository.findAllOrders();
    }
}
