package com.apex.tech3.wallt_app.exceptions;

public class AuthenticationFailureException extends RuntimeException{

    public AuthenticationFailureException(String message) {
        super(message);
    }
}
