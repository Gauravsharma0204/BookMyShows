package com.backend.bookmyshows.dto;

import com.backend.bookmyshows.entity.Theater;

public record TheaterResponse(Long id, String name, String city, String address) {

    public static TheaterResponse from(Theater theater){
        return new TheaterResponse(theater.getId(), theater.getName(),
                   theater.getCity(), theater.getAddress());
    }
}
