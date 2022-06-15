package com.adrian99.onlineShop.repository;

import com.adrian99.onlineShop.model.User;
import com.adrian99.onlineShop.repository.custom.UserCustomRepository;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long>,
        UserCustomRepository {
}
