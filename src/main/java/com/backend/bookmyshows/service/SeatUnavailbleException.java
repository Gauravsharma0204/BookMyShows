package com.backend.bookmyshows.service;

public class SeatUnavailbleException extends RuntimeException{

    public SeatUnavailbleException(String msg){
        super(msg);
    }
}
