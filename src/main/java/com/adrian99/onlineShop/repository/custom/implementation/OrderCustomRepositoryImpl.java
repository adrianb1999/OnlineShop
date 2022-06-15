package com.adrian99.onlineShop.repository.custom.implementation;

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
    public Map<String, Object> findOrderById(Long id) {
        JPAQuery<Order> orderQuery = new JPAQuery<>(entityManager);
        QOrder qOrder = QOrder.order;

        JPAQuery<OrderProduct> orderProductQuery = new JPAQuery<>(entityManager);
        QOrderProduct qOrderProduct = QOrderProduct.orderProduct;

        Order order = orderQuery.select(qOrder)
                .from(qOrder)
                .where(qOrder.id.eq(id))
                .fetchFirst();

        List<OrderProduct> fetch = orderProductQuery.select(qOrderProduct).from(qOrderProduct).where(qOrderProduct.order.id.eq(id)).fetch();

        List<Map<String, Object>> productsList = new ArrayList<>();

        for (var product : fetch) {
            productsList.add(Map.of("productId", product.getId(),
                    "amount", product.getAmount()));
        }

        return orderMapFilling(order, productsList);
    }

    @Override
    public List<Map<String, Object>> findAllOrders() {

        JPAQuery<Order> orderQuery = new JPAQuery<>(entityManager);
        QOrder qOrder = QOrder.order;

        JPAQuery<OrderProduct> orderProductQuery = new JPAQuery<>(entityManager);
        QOrderProduct qOrderProduct = QOrderProduct.orderProduct;

        List<Order> orderList = orderQuery.select(qOrder)
                .from(qOrder)
                .fetch();

        List<OrderProduct> orderProductList = orderProductQuery.select(qOrderProduct)
                .from(qOrderProduct)
                .fetch();

        List<Map<String, Object>> orderMapList = new ArrayList<>();

        for (Order order : orderList) {
            List<Map<String, Object>> productsList = new ArrayList<>();

            for (OrderProduct orderProduct : orderProductList) {
                if (Objects.equals(orderProduct.getOrder().getId(), order.getId())) {
                    productsList.add(Map.of(
                            "productId", orderProduct.getId(),
                            "amount", orderProduct.getAmount()));
                }
            }
            orderMapList.add(orderMapFilling(order, productsList));
        }

        return orderMapList;
    }

    private Map<String, Object> orderMapFilling(Order order, List<Map<String, Object>> productList) {

        Map<String, Object> orderMap = new HashMap<>();

        orderMap.put("products", productList);
        orderMap.put("orderId", order.getId());
        orderMap.put("value", order.getValue());
        orderMap.put("status", order.getOrderStatus());
        orderMap.put("billingAddress", order.getBillingAddress().getFullAddress());
        orderMap.put("shippingAddress", order.getShippingAddress().getFullAddress());
        if (order.getUser() != null)
            orderMap.put("userId", order.getUser().getId());
        else
            orderMap.put("userId", "");
        orderMap.put("shippingFee", order.getShippingFee());

        return orderMap;
    }

}
