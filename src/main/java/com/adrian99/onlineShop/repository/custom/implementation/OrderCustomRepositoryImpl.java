package com.adrian99.onlineShop.repository.custom.implementation;

import com.adrian99.onlineShop.dto.OrderDTO;
import com.adrian99.onlineShop.dto.OrderProductDTO;
import com.adrian99.onlineShop.model.*;
import com.adrian99.onlineShop.repository.custom.OrderCustomRepository;
import com.querydsl.jpa.impl.JPAQuery;

import javax.persistence.EntityManager;
import java.util.*;

public class OrderCustomRepositoryImpl implements OrderCustomRepository {

    private final EntityManager entityManager;

    public OrderCustomRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public OrderDTO findOrderById(Long id) {

        JPAQuery<Order> orderQuery = new JPAQuery<>(entityManager);
        QOrder qOrder = QOrder.order;

        Order order = orderQuery.select(qOrder)
                .from(qOrder)
                .where(qOrder.id.eq(id))
                .fetchFirst();

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.addOrder(order);
        orderDTO.addUser(order.getUser());
        orderDTO.setOrderProductDTOList(getOrderProducts(order));

        return orderDTO;
    }

    @Override
    public List<OrderDTO> findAllOrders() {

        JPAQuery<Order> orderQuery = new JPAQuery<>(entityManager);
        QOrder qOrder = QOrder.order;

        List<Order> orderList = orderQuery.select(qOrder)
                .from(qOrder)
                .orderBy(qOrder.id.asc())
                .fetch();

        List<OrderDTO> orderDTOList = new ArrayList<>();

        for (Order order : orderList) {
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.addOrder(order);
            orderDTO.addUser(order.getUser());
            orderDTO.setOrderProductDTOList(getOrderProducts(order));

            orderDTOList.add(orderDTO);
        }

        return orderDTOList;
    }
    private List<OrderProductDTO> getOrderProducts(Order order){

        JPAQuery<OrderProduct> orderProductQuery = new JPAQuery<>(entityManager);
        QOrderProduct qOrderProduct = QOrderProduct.orderProduct;
        List<OrderProductDTO> productsList = new ArrayList<>();

        List<OrderProduct> fetch = orderProductQuery.select(qOrderProduct)
                .from(qOrderProduct)
                .where(qOrderProduct.order.id.eq(order.getId()))
                .fetch();

        for (var product : fetch) {
            productsList.add(new OrderProductDTO(product));
        }

        return productsList;
    }
}
