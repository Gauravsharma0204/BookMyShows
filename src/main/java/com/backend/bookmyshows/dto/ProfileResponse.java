package com.backend.bookmyshows.dto;

import com.backend.bookmyshows.entity.Customer;

public record ProfileResponse(Long id, String name, String email, String phone) {
    public static ProfileResponse from (Customer customer){
        return new ProfileResponse(customer.getId(),
                customer.getName(), customer.getEmail(),
                customer.getPhone());
    }

}
