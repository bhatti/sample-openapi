package org.plexobject.demo.services.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.plexobject.demo.services.exceptions.ValidationException;

import javax.money.MonetaryAmount;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

public class Payment {
    private String id = UUID.randomUUID().toString();

    @NotBlank
    @Min(30)
    @Max(36)
    private String customerId;

    @NotBlank
    private PaymentStatus status;

    @NotBlank
    private CreditCard creditCard;

    @JsonProperty
    @Min(1)
    @Max(1000)
    private MonetaryAmount amount;

    public Payment() {

    }
    public Payment(String customerId, PaymentStatus status, CreditCard creditCard, MonetaryAmount amount) {
        this.customerId = customerId;
        this.status = status;
        this.creditCard = creditCard;
        this.amount = amount;
    }

    public void validate() throws ValidationException {
        ValidationException validation = new ValidationException();
        validation.checkNullEmpty(customerId, "empty name");
        validation.checkNull(status, "empty status");
        validation.checkNull(creditCard, "empty creditCard");
        validation.checkNullZero(amount, "empty amount");
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

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    public MonetaryAmount getAmount() {
        return amount;
    }

    public void setAmount(MonetaryAmount amount) {
        this.amount = amount;
    }
}
