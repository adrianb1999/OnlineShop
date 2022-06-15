package com.adrian99.onlineShop.repository.custom;

import com.adrian99.onlineShop.model.User;

public interface UserCustomRepository {
    User findByUsername(String username);
    User findByEmail(String email);

}
