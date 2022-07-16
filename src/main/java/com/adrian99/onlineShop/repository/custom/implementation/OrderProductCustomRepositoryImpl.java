package com.adrian99.onlineShop.repository.custom.implementation;

import com.adrian99.onlineShop.model.OrderProduct;
import com.adrian99.onlineShop.model.QOrderProduct;
import com.adrian99.onlineShop.repository.custom.OrderProductCustomRepository;
import com.querydsl.jpa.impl.JPAQuery;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderProductCustomRepositoryImpl implements OrderProductCustomRepository {

    private final EntityManager entityManager;

    public OrderProductCustomRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<OrderProduct> findAllOrderProductByOrderId(Long id) {

        JPAQuery<OrderProduct> orderQuery = new JPAQuery<>(entityManager);
        QOrderProduct qOrderProduct = QOrderProduct.orderProduct;

        return orderQuery.select(qOrderProduct)
                .from(qOrderProduct)
                .where(qOrderProduct.order.id.eq(id))
                .fetch();
    }

    @Override
    public Map<Long, OrderProduct> findAllOrderProductByOrderIdMap(Long id) {
        List<OrderProduct> productList = findAllOrderProductByOrderId(id);

        Map<Long, OrderProduct> orderProductMap = new HashMap<>();

        for(OrderProduct orderProduct : productList){
            orderProductMap.put(orderProduct.getProduct().getId(), orderProduct);
        }

        return orderProductMap;
    }
}
