package com.example.sebackend.error;

public class UnknownError implements ServiceError{

    @Override
    public String getMessage() {
        return "Unknown Error!";
    }

    @Override
    public Integer getCode() {return -1;}
}
