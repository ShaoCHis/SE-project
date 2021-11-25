package com.example.sebackend.error;

public class EmailFailedError implements ServiceError{

    @Override
    public String getMessage() {
        return "Email-send is failed!";
    }

    @Override
    public Integer getCode() { return 4;}
}
