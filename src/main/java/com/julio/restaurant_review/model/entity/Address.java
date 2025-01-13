package com.julio.restaurant_review.model.entity;

import com.julio.restaurant_review.model.dto.AddressDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity
@Table(name = "address")
public class Address implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String street;

    private String number;

    private String city;

    private String neighborhood;

    private String state;

    private String zipCode;

    private String country;

    public static Address fromDTO(AddressDTO addressDTO) {
        Address address = new Address();
        address.setCity(addressDTO.city());
        address.setStreet(addressDTO.street());
        address.setZipCode(addressDTO.zipCode());
        address.setNumber(addressDTO.number());
        address.setState(addressDTO.state());
        address.setCountry(addressDTO.country());
        address.setNeighborhood(addressDTO.neighborhood());
        return address;
    }

    public static AddressDTO toDTO(Address address) {
        return new AddressDTO(
                address.getCity(),
                address.getStreet(),
                address.getNeighborhood(),
                address.getNumber(),
                address.getZipCode(),
                address.getState(),
                address.getCountry()
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
