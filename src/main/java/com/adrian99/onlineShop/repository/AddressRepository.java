package com.adrian99.onlineShop.repository;

import com.adrian99.onlineShop.model.Address;
import com.adrian99.onlineShop.repository.custom.AddressCustomRepository;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, Long>, AddressCustomRepository {
}
