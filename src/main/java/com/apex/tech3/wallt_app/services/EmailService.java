package com.apex.tech3.wallt_app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendConfirmationEmail(String email, String confirmationToken) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Email Confirmation");
        message.setText("Please click the link below to confirm your email:\n\n"
                + "http://localhost/api/users/confirm?token=" + confirmationToken); // Assuming your application runs on localhost:8080
        javaMailSender.send(message);
    }
}
