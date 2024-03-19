package com.apex.tech3.wallt_app;

import com.apex.tech3.wallt_app.models.*;
import com.apex.tech3.wallt_app.models.enums.StatusEnum;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;


public class Helpers {

    public static User createMockUser() {
        User mockUser = new User();
        mockUser.setId(1);
        mockUser.setUsername("MockUsername");
        mockUser.setPassword("MockPassword");
        mockUser.setFirstName("MockFirstName");
        mockUser.setLastName("MockLastName");
        mockUser.setEmail("mock@user.com");
        mockUser.setRoles(Set.of(createMockUserRole()));
        return mockUser;
    }

    public static User createMockAdmin() {
        User mockUser = new User();
        mockUser.setId(1);
        mockUser.setUsername("MockUsername");
        mockUser.setPassword("MockPassword");
        mockUser.setFirstName("MockFirstName");
        mockUser.setLastName("MockLastName");
        mockUser.setEmail("mock@user.com");
        mockUser.setRoles(new HashSet<>(Set.of(createMockAdminRole(), createMockUserRole())));
        return mockUser;
    }


    public static Card createMockCard() {
        Card card = new Card();
        card.setId(1);
        card.setHolder(createMockUser());
        card.setNumber("1234567891011121");
        card.setExpirationMonth("12");
        card.setExpirationYear("2025");
        card.setCvv("123");
        card.setActive(true);
        card.setStampCreated(Timestamp.valueOf("2021-01-01 00:00:00"));
        return card;
    }

    public static Currency createMockCurrency() {
        Currency currency = new Currency();
        currency.setId(1);
        currency.setName("US Dollar");
        currency.setSymbol("$");
        currency.setTicker("USD");
        currency.setRateToUsd(1);
        return currency;
    }

    public static Wallet createMockWallet() {
        Wallet wallet = new Wallet();
        wallet.setId(1);
        wallet.setAmount(BigDecimal.valueOf(1000));
        wallet.setCurrency(createMockCurrency());
        wallet.setHolder(createMockUser());
        wallet.setActive(true);
        wallet.setStampCreated(Timestamp.valueOf("2021-01-01 00:00:00"));
        return wallet;
    }


    public static Role createMockUserRole() {
        Role userRole = new Role();
        userRole.setId(1);
        userRole.setName("USER");
        return userRole;
    }

    public static Role createMockAdminRole() {
        Role adminRole = new Role();
        adminRole.setId(2);
        adminRole.setName("ADMIN");
        return adminRole;
    }

    public static Transaction createMockTransaction() {
        Transaction transaction = new Transaction();
        transaction.setId(1);
        transaction.setSenderWallet(createMockWallet());
        transaction.setReceiverWallet(createMockWallet());
        transaction.setAmount(BigDecimal.valueOf(100));
        transaction.setExchangeRate(1);
        transaction.setStatus(StatusEnum.SUCCESSFUL);
        transaction.setStampCreated(Timestamp.valueOf("2021-01-01 00:00:00"));
        return transaction;
    }


}
