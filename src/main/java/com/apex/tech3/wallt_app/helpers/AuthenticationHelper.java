package com.apex.tech3.wallt_app.helpers;

import com.apex.tech3.wallt_app.exceptions.AuthenticationFailureException;
import com.apex.tech3.wallt_app.exceptions.AuthorizationException;
import com.apex.tech3.wallt_app.exceptions.EntityNotFoundException;
import com.apex.tech3.wallt_app.models.User;
import com.apex.tech3.wallt_app.services.contracts.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationHelper {
    private static final String AUTHORIZATION_HEADER_NAME = "Authorization";
    private static final String INVALID_AUTHENTICATION_ERROR = "Invalid authentication.";
    private static final String INVALID_AUTHORIZATION_ERROR = "You are blocked.";
    public static final String AUTHENTICATION_FAILURE_MESSAGE = "Wrong username or password";


    private final UserService userService;

    @Autowired
    public AuthenticationHelper(UserService userService) {
        this.userService = userService;
    }

    public static boolean isAdmin(User user) {
        return user.getRoles().stream().anyMatch(r -> r.getName().equals("ADMIN"));
    }
    public static boolean isBlocked(User user) {
        return user.isBlocked();
    }


    public User tryGetUser(HttpHeaders headers)  {
        if (!headers.containsKey(AUTHORIZATION_HEADER_NAME)) {
            throw new AuthorizationException(INVALID_AUTHENTICATION_ERROR);
        }

        try {
            String userInfo = headers.getFirst(AUTHORIZATION_HEADER_NAME);
            String username = getUsername(userInfo);
            String password = getPassword(userInfo);
            User user = userService.getByUsername(username);
            if (!user.getPassword().equals(password)) {
                throw new AuthenticationFailureException(INVALID_AUTHENTICATION_ERROR);
            }
            if (user.isBlocked()) {
                throw new AuthorizationException(INVALID_AUTHORIZATION_ERROR);
            }
            return user;
        } catch (EntityNotFoundException e) {
            throw new AuthenticationFailureException(INVALID_AUTHENTICATION_ERROR);
        }
    }

    public User tryGetUsername(HttpSession httpSession) {
        String currentUser = (String) httpSession.getAttribute("currentUser");

        if (currentUser == null) throw new AuthenticationFailureException("no user logged in");

        return userService.getByUsername(currentUser);
    }

    private String getUsername(String userInfo) {
        int firstSpace = userInfo.indexOf(" ");
        if (firstSpace == -1) {
            throw new AuthorizationException(INVALID_AUTHENTICATION_ERROR);
        }

        return userInfo.substring(0, firstSpace);
    }

    private String getPassword(String userInfo) {
        int firstSpace = userInfo.indexOf(" ");
        if (firstSpace == -1) {
            throw new AuthorizationException(INVALID_AUTHENTICATION_ERROR);
        }

        return userInfo.substring(firstSpace + 1);
    }

    public User verifyAuthentication(String username, String password) {
        try {
            User user = userService.getByUsername(username);
            if (!user.getPassword().equals(password)) {
                throw new AuthenticationFailureException(AUTHENTICATION_FAILURE_MESSAGE);
            }
            return user;
        } catch (EntityNotFoundException e) {
            throw new AuthenticationFailureException(AUTHENTICATION_FAILURE_MESSAGE);
        }
    }

    public User tryGetCurrentUser(HttpSession session) {
        String currentUsername = (String) session.getAttribute("currentUser");

        if (currentUsername == null) {
            throw new AuthenticationFailureException(INVALID_AUTHENTICATION_ERROR);
        }

        return userService.getByUsername(currentUsername);
    }
}
