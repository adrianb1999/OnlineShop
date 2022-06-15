package com.adrian99.onlineShop.service.implementation;

import com.adrian99.onlineShop.model.User;
import com.adrian99.onlineShop.repository.UserRepository;
import com.adrian99.onlineShop.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Iterable<User> findAll() {
        return null;
    }

    @Override
    public User findById(Long aLong) {
        return userRepository.findById(aLong).orElse(null);
    }

    @Override
    public User save(User object) {
        return userRepository.save(object);
    }

    @Override
    public <S extends User> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public String passwordGenerator(int length) {
        StringBuilder password = new StringBuilder();
        Random random = new Random();

        for(int i = 0; i < length; i++){
            char c = (char) (random.nextInt(26) + 65);
            boolean lower = random.nextBoolean();

            if(lower)
                c = (char) (c + 32);

            password.append(c);
        }
        return password.toString();
    }

    @Override
    public boolean isEmailValid(String email) {
        return email.matches("^(.+)@(\\S+)$");
    }

    @Override
    public String generateToken(User user) {
        return null;
    }

    @Override
    public boolean isPhoneNumberValid(String phoneNumber) {
        return true;
    }
}
