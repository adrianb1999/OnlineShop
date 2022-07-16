package com.adrian99.onlineShop.service.implementation;

import com.adrian99.onlineShop.dto.AddressDTO;
import com.adrian99.onlineShop.exception.ApiRequestException;
import com.adrian99.onlineShop.model.Address;
import com.adrian99.onlineShop.model.User;
import com.adrian99.onlineShop.repository.AddressRepository;
import com.adrian99.onlineShop.service.AddressService;
import com.adrian99.onlineShop.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final UserService userService;

    public AddressServiceImpl(AddressRepository addressRepository, UserService userService) {
        this.addressRepository = addressRepository;
        this.userService = userService;
    }

    @Override
    public Iterable<Address> findAll() {
        return addressRepository.findAll();
    }

    @Override
    public Address findById(Long aLong) {
        return addressRepository.findById(aLong).orElse(null);
    }

    @Override
    public Address save(Address object) {
        return addressRepository.save(object);
    }

    @Override
    public <S extends Address> Iterable<S> saveAll(Iterable<S> entities) {
        return addressRepository.saveAll(entities);
    }

    @Override
    public void deleteById(Long aLong) {
        addressRepository.deleteById(aLong);
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {
        addressRepository.deleteAllById(longs);
    }

    @Override
    public Address addAddress(Address address) {
        if(address.getFullAddress() == null || address.getFullAddress().isBlank() ||
                address.getFullName() == null || address.getFullName().isBlank() ||
                address.getPhoneNumber() == null || address.getPhoneNumber().isBlank())
            throw new ApiRequestException("The address is invalid!");

        if(!userService.isPhoneNumberValid(address.getPhoneNumber()))
            throw new ApiRequestException("The phone number is invalid!");

        return save(address);
    }

    @Override
    public List<Address> findByUser(User user) {
        return addressRepository.findByUser(user);
    }
}
