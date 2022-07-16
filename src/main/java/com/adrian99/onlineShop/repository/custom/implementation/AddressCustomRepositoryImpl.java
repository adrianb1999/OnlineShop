package com.adrian99.onlineShop.repository.custom.implementation;

import com.adrian99.onlineShop.model.Address;
import com.adrian99.onlineShop.model.Order;
import com.adrian99.onlineShop.model.QAddress;
import com.adrian99.onlineShop.model.User;
import com.adrian99.onlineShop.repository.custom.AddressCustomRepository;
import com.querydsl.jpa.impl.JPAQuery;

import javax.persistence.EntityManager;
import java.util.List;

public class AddressCustomRepositoryImpl implements AddressCustomRepository {

    private final EntityManager entityManager;

    public AddressCustomRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Address> findByUser(User user) {
        JPAQuery<Order> orderQuery = new JPAQuery<>(entityManager);
        QAddress address = QAddress.address;

        return orderQuery.select(address)
                    .from(address)
                    .where(address.user.eq(user))
                    .fetch();
    }
}
