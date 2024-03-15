package com.apex.tech3.wallt_app.models.dtos;

import com.apex.tech3.wallt_app.models.dtos.interfaces.Register;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class CardDto {
    @NotEmpty(message = "Card number cannot be empty")
    @Length(min = 2, max = 30, message = "Card holder name must be between 2 and 30 characters", groups = {Register.class})
    private String cardHolderName;
    @NotEmpty(message = "Cardholder name cannot be empty")
    @Length(min = 16, max = 16, message = "Card number must be 16 digits", groups = {Register.class})
    private String cardNumber;
    @NotEmpty(message = "Expiration month cannot be empty")
    @Length(min = 2, max = 2, message = "Expiration month must be 2 digits", groups = {Register.class})
    private String expirationMonth;
    @NotEmpty(message = "Expiration year cannot be empty")
    @Length(min = 2, max = 2, message = "Expiration year must be 2 digits", groups = {Register.class})
    private String expirationYear;
    @NotEmpty(message = "CVV cannot be empty")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Length(min = 3, max = 3, message = "CVV must be 3 digits", groups = {Register.class})
    private String cvv;


}
