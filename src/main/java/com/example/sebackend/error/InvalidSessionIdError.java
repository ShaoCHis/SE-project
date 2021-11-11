package com.example.sebackend.error;

public class InvalidSessionIdError implements ServiceError{

    @Override
    public String getMessage() {
        return "SessionId is Invalid!";
    }

    @Override
    public Integer getCode() { return 1;}
}
