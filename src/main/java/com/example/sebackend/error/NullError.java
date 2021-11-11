package com.example.sebackend.error;

public class NullError implements ServiceError{

    @Override
    public String getMessage() {
        return "Something is null!";
    }

    @Override
    public Integer getCode() {return 2;}
}
