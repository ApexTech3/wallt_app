package com.apex.tech3.wallt_app.services;

import com.apex.tech3.wallt_app.exceptions.EntityNotFoundException;
import com.apex.tech3.wallt_app.exceptions.InvalidTokenException;
import com.apex.tech3.wallt_app.models.User;
import com.apex.tech3.wallt_app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserConfirmationService {
    private final TokenService tokenService;

    private final EmailSender emailSender;

    private final UserRepository userRepository;

    public UserConfirmationService(TokenService tokenService, EmailSender emailSender, UserRepository userRepository) {
        this.tokenService = tokenService;
        this.emailSender = emailSender;
        this.userRepository = userRepository;
    }

    public void sendConfirmationEmail(User user) {
        String confirmationToken = tokenService.generateToken();
        user.setConfirmationToken(confirmationToken);
        userRepository.save(user);
        emailSender.sendConfirmationEmail(user.getEmail(), confirmationToken);
    }

    public void confirmUser(String token) {
        User user = userRepository.findByConfirmationToken(token);
        if (user == null || user.isVerified()) {
            throw new EntityNotFoundException("User", "confirmation token", token);
        }
        if (tokenService.isValidToken(token)) {
            user.setVerified(true);
            user.setConfirmationToken(null); // Invalidate the token
            userRepository.save(user);
        } else {
            throw new InvalidTokenException("Invalid confirmation token");
        }
    }
}