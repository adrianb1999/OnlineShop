package com.adrian99.onlineShop.service.implementation;

import com.adrian99.onlineShop.model.Address;
import com.adrian99.onlineShop.repository.AddressRepository;
import com.adrian99.onlineShop.service.AddressService;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public Iterable<Address> findAll() {
        return null;
    }

    @Override
    public Address findById(Long aLong) {
        return null;
    }

    @Override
    public Address save(Address object) {
        return addressRepository.save(object);
    }

    @Override
    public <S extends Address> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }
}
