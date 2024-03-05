package com.apex.tech3.wallt_app.services.contracts;

public interface EmailService {

    public void sendConfirmationEmail(String email, String confirmationToken);
}
