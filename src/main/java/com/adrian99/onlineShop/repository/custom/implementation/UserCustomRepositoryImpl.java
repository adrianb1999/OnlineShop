package com.adrian99.onlineShop.repository.custom.implementation;

import com.adrian99.onlineShop.model.QUser;
import com.adrian99.onlineShop.model.User;
import com.adrian99.onlineShop.repository.custom.UserCustomRepository;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

public class UserCustomRepositoryImpl implements UserCustomRepository {

    private final EntityManager entityManager;

    public UserCustomRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    @Override
    public User findByUsername(String username) {
        JPAQuery<User> query = new JPAQuery<>(entityManager);
        QUser qUser = QUser.user;
        return query.select(qUser).from(qUser)
                .where(qUser.username.eq(username)).fetchFirst();
    }

    @Transactional
    @Override
    public User findByEmail(String email) {
        JPAQuery<User> query = new JPAQuery<>(entityManager);
        QUser qUser = QUser.user;
        return query.select(qUser).from(qUser)
                .where(qUser.email.eq(email)).fetchFirst();
    }
}
