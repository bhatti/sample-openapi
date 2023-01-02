package org.plexobject.demo.services.controller;

import org.plexobject.demo.services.exceptions.CreditCardExpiredException;
import org.plexobject.demo.services.exceptions.CreditLimitExceededException;
import org.plexobject.demo.services.exceptions.PaymentNotFoundException;
import org.plexobject.demo.services.model.CreditCard;
import org.plexobject.demo.services.model.Payment;
import org.springframework.web.bind.annotation.*;

import javax.money.MonetaryAmount;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@RestController
public class PaymentController {

    private final Map<String, Payment> payments = new ConcurrentHashMap<>();
    private final Map<String, CreditCard> creditCards = new ConcurrentHashMap<>();

    @PostMapping("/payments")
    public Payment postPayment(@Valid @RequestBody Payment payment) {
        if (payment.getCreditCard().getBalance().isNegativeOrZero()) {
            throw new CreditLimitExceededException();
        }
        Calendar cal = Calendar.getInstance();
        if (payment.getCreditCard().expired()) {
            throw new CreditCardExpiredException();
        }
        payment.setId(UUID.randomUUID().toString());
        payments.put(payment.getId(), payment);
        creditCards.put(payment.getCreditCard().getId(), payment.getCreditCard());
        return payment;
    }

    @PostMapping(path = "/payments/{id}/{amount}")
    public Payment charge(
            @PathVariable(name = "id") @NotBlank @Size(max = 36) String id,
            @PathVariable(name = "amount") @NotBlank MonetaryAmount amount) {
        Payment payment = payments.get(id);
        if (payment == null) {
            throw new PaymentNotFoundException();
        }
        if (payment.getCreditCard().getBalance().isNegativeOrZero()) {
            throw new CreditLimitExceededException();
        }
        if (payment.getCreditCard().expired()) {
            throw new CreditCardExpiredException();
        }
        if (payment.getCreditCard().getBalance().isLessThan(amount)) {
            throw new CreditLimitExceededException();
        }
        payment.getCreditCard().setBalance(payment.getCreditCard().getBalance().subtract(amount));
        return payment;
    }

    @GetMapping(path = "/payments/{id}")
    public Payment getPayment(@PathVariable(name = "id") @NotBlank @Size(max = 36) String id) {
        Payment payment = payments.get(id);
        if (payment == null) {
            throw new PaymentNotFoundException();
        }
        return payment;
    }

    @GetMapping(path = "/payments")
    public List<Payment> getPaymentByCustomer(@RequestParam(name = "id", defaultValue = "") @Size(max = 36) String id) {
        return payments.values().stream().filter(p -> "".equals(id) || p.getCustomerId().equals(id)).collect(Collectors.toList());
    }
}

