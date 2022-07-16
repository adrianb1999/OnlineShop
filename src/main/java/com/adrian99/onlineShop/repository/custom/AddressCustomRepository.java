package com.adrian99.onlineShop.repository.custom;

import com.adrian99.onlineShop.dto.AddressDTO;
import com.adrian99.onlineShop.model.Address;
import com.adrian99.onlineShop.model.User;

import java.util.List;

public interface AddressCustomRepository {
    List<Address> findByUser(User user);
}
