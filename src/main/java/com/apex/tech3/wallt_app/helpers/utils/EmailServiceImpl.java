package com.apex.tech3.wallt_app.helpers.utils;

import com.apex.tech3.wallt_app.services.contracts.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendConfirmationEmail(String email, String confirmationToken) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Email Confirmation");
        message.setText("Please click the link below to confirm your email:\n\n"
                                + "https://wallt.tech/api/users/confirm?token=" + confirmationToken); // Assuming the application runs on localhost
        javaMailSender.send(message);
    }

    @Override
    public void sendResetPasswordEmail(String email, String confirmationToken) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Email Confirmation");
        message.setText("Please click the link below to reset your password:\n\n"
                + "https://wallt.tech/auth/passwordReset?token=" + confirmationToken); // Assuming the application runs on localhost
        javaMailSender.send(message);
    }

}
