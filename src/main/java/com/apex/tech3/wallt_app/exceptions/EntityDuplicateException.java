package com.apex.tech3.wallt_app.exceptions;

public class EntityDuplicateException extends RuntimeException {

    public EntityDuplicateException(String type, String attribute, String value) {
        super(String.format("%s with %s %s already exists.", type, attribute, value));
    }

}
