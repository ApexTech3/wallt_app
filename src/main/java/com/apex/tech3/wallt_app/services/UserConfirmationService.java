package com.apex.tech3.wallt_app.services;

import com.apex.tech3.wallt_app.exceptions.EntityNotFoundException;
import com.apex.tech3.wallt_app.exceptions.InvalidTokenException;
import com.apex.tech3.wallt_app.models.User;
import com.apex.tech3.wallt_app.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserConfirmationService {
    private final TokenService tokenService;

    private final EmailService emailService;

    private final UserRepository userRepository;

    public UserConfirmationService(TokenService tokenService, EmailService emailService, UserRepository userRepository) {
        this.tokenService = tokenService;
        this.emailService = emailService;
        this.userRepository = userRepository;
    }

    public void sendConfirmationEmail(User user) {
        String confirmationToken = tokenService.generateToken();
        user.setConfirmationToken(confirmationToken);
        emailService.sendConfirmationEmail(user.getEmail(), confirmationToken);
        userRepository.save(user);
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