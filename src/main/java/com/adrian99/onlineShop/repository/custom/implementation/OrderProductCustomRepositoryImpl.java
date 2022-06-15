package com.adrian99.onlineShop.repository.custom.implementation;

import com.adrian99.onlineShop.model.OrderProduct;
import com.adrian99.onlineShop.model.QOrderProduct;
import com.adrian99.onlineShop.repository.custom.OrderProductCustomRepository;
import com.querydsl.jpa.impl.JPAQuery;

import javax.persistence.EntityManager;
import java.util.List;

public class OrderProductCustomRepositoryImpl implements OrderProductCustomRepository {

    private final EntityManager entityManager;

    public OrderProductCustomRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<OrderProduct> findAllOrderProductByOrderId(Long id) {

        JPAQuery<OrderProduct> orderQuery = new JPAQuery<>(entityManager);
        QOrderProduct qOrderProduct = QOrderProduct.orderProduct;

        List<OrderProduct> orderProducts = orderQuery.select(qOrderProduct)
                .from(qOrderProduct)
                .where(qOrderProduct.product.id.eq(id))
                .fetch();

        return orderProducts;
    }
}
