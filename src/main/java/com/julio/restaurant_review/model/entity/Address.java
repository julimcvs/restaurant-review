package com.julio.restaurant_review.model.entity;

import com.julio.restaurant_review.model.dto.AddressDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "address")
@Getter
@Setter
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
}
