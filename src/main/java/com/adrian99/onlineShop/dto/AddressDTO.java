package com.adrian99.onlineShop.dto;

import com.adrian99.onlineShop.model.Address;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class AddressDTO {
    private Long id;
    private String fullAddress;
    private String phoneNumber;
    private String fullName;
    private Long userId;

    public AddressDTO(Long id, String fullAddress, String phoneNumber, String fullName, Long userId) {
        this.id = id;
        this.fullAddress = fullAddress;
        this.phoneNumber = phoneNumber;
        this.fullName = fullName;
        this.userId = userId;
    }

    public AddressDTO() {
    }

    public AddressDTO(Address address){
        this.id = address.getId();
        this.fullAddress = address.getFullAddress();
        this.phoneNumber = address.getPhoneNumber();
        this.fullName = address.getFullName();
        if(address.getUser() != null)
            this.userId = address.getUser().getId();
    }

    @JsonIgnore
    public Address getAddress(){
        Address address = new Address();
        address.setId(this.id);
        address.setFullAddress(this.fullAddress);
        address.setFullName(this.fullName);
        address.setPhoneNumber(this.phoneNumber);
        return address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
