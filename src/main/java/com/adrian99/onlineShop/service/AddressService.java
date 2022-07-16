package com.adrian99.onlineShop.service;

import com.adrian99.onlineShop.model.Address;
import com.adrian99.onlineShop.repository.custom.AddressCustomRepository;

public interface AddressService extends CrudService<Address, Long>, AddressCustomRepository {
    Address addAddress(Address address);
}
