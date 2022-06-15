package com.adrian99.onlineShop.model;

import javax.persistence.*;

@Entity
@Table(name = "Addresses")
public class Address {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    private String fullAddress;

    private String phoneNumber;

    private String fullName;

    @OneToOne
    private User user;

    public Address() {
    }

    public Address(String fullAddress, String phoneNumber, String fullName, User user) {
        this.fullAddress = fullAddress;
        this.phoneNumber = phoneNumber;
        this.fullName = fullName;
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
}
