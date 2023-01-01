package org.plexobject.demo.services.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.plexobject.demo.services.exceptions.ValidationException;

import javax.money.MonetaryAmount;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class CreditCard {
    private String id;

    @NotBlank
    @Size(min = 30, max = 36)
    private String customerId;

    private CreditCardType type;

    @Size(min = 10, max = 20)
    @NotBlank
    @Pattern(regexp = "\\d{4}-\\d{4}-\\d{4}", message = "Please provide a valid credit card number")
    private String cardNumber;

    @NotBlank
    @Pattern(regexp = "\\d{2}/\\d{4}", message = "Please provide a valid credit card expiration")
    private String expiration;

    @JsonProperty
    @Min(1)
    @Max(10000)
    private MonetaryAmount balance;

    public CreditCard(String customerId, CreditCardType type, String cardNumber, String expiration, MonetaryAmount balance) {
        this.customerId = customerId;
        this.type = type;
        this.cardNumber = cardNumber;
        this.expiration = expiration;
        this.balance = balance;
    }

    public void validate() throws ValidationException {
        ValidationException validation = new ValidationException();
        validation.checkNullEmpty(customerId, "empty customerId");
        validation.checkNull(type, "empty creditCardType");
        validation.checkNullEmpty(cardNumber, "empty cardNumber");
        validation.checkNull(expiration, "empty expiration");
        validation.checkNullZero(balance, "empty balance");
        validation.checkThrow();
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public CreditCardType getType() {
        return type;
    }

    public void setType(CreditCardType type) {
        this.type = type;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public boolean expired() {
        String now = DateTimeFormatter.ofPattern("yyyyMM", Locale.US).format(LocalDateTime.now());
        String exp = expiration.substring(3) + expiration.substring(0, 2);
        return exp.compareTo(now) < 0;
    }

    public MonetaryAmount getBalance() {
        return balance;
    }

    public void setBalance(MonetaryAmount balance) {
        this.balance = balance;
    }
}
