package com.adrian99.onlineShop.controller;

import com.adrian99.onlineShop.dto.AddressDTO;
import com.adrian99.onlineShop.exception.ApiRequestException;
import com.adrian99.onlineShop.model.Address;
import com.adrian99.onlineShop.model.User;
import com.adrian99.onlineShop.service.AddressService;
import com.adrian99.onlineShop.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
public class AddressController {
    private final AddressService addressService;
    private final UserService userService;

    public AddressController(AddressService addressService, UserService userService) {
        this.addressService = addressService;
        this.userService = userService;
    }

    @GetMapping("/api/address")
    public List<AddressDTO> getUserAddress(Principal principal) {
        User user = null;
        if(principal != null)
            user = userService.findByUsername(principal.getName());


        List<AddressDTO> addressDTOList = new ArrayList<>();
        for(Address address : addressService.findByUser(user)){
            addressDTOList.add(new AddressDTO(address));
        }

        return addressDTOList;
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
