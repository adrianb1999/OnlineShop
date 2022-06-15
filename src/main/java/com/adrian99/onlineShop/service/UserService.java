package com.adrian99.onlineShop.service;

import com.adrian99.onlineShop.model.User;
import com.adrian99.onlineShop.repository.custom.UserCustomRepository;

public interface UserService extends CrudService<User, Long>, UserCustomRepository {
    String passwordGenerator(int length);
    boolean isEmailValid(String email);
    String generateToken(User user);
    boolean isPhoneNumberValid(String phoneNumber);
}
