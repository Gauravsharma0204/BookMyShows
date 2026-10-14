package com.backend.bookmyshows.service;

public class ProfileConflictException  extends RuntimeException{
    public ProfileConflictException(String msg){
        super(msg);
    }
}
