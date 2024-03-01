package com.apex.tech3.wallt_app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSender {
    private final JavaMailSender javaMailSender;

    public EmailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendConfirmationEmail(String email, String confirmationToken) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Email Confirmation");
        message.setText("Please click the link below to confirm your email:\n\n"
                + "http://yourwebsite.com/confirm?token=" + confirmationToken);
        javaMailSender.send(message);
    }
}
