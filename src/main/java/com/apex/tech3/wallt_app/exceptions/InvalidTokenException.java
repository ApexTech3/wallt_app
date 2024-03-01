package com.apex.tech3.wallt_app.exceptions;


public class InvalidTokenException extends RuntimeException{

    public InvalidTokenException(String message) {
        super(message);
    }
}
