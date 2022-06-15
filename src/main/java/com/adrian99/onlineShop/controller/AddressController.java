package com.adrian99.onlineShop.controller;

import com.adrian99.onlineShop.exception.ApiRequestException;
import com.adrian99.onlineShop.model.Address;
import com.adrian99.onlineShop.model.User;
import com.adrian99.onlineShop.service.AddressService;
import com.adrian99.onlineShop.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class AddressController {
    private final AddressService addressService;
    private final UserService userService;

    public AddressController(AddressService addressService, UserService userService) {
        this.addressService = addressService;
        this.userService = userService;
    }

    @PostMapping("/api/address")
    public Address addAddress(@RequestBody Address address, Principal principal){
        if(principal == null)
            throw new ApiRequestException("Invalid user");
        User currentUser = userService.findByUsername(principal.getName());

        if(currentUser == null)
            throw new ApiRequestException("User do not exists!");

        address.setUser(currentUser);

        return addressService.save(address);
    }
}
