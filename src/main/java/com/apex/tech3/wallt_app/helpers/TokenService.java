package com.apex.tech3.wallt_app.helpers;

import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class TokenService {

    private static final Pattern TOKEN_PATTERN = Pattern.compile("[a-f0-9]{8}(-[a-f0-9]{4}){3}-[a-f0-9]{12}");

    public String generateToken() {
        return UUID.randomUUID().toString();
    }

    public boolean isValidToken(String token) {
        return token != null && TOKEN_PATTERN.matcher(token).matches();
    }
}
