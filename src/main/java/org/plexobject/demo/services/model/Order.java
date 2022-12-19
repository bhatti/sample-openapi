package org.plexobject.demo.services.model;

import org.plexobject.demo.services.exceptions.ValidationException;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

public class Order {
    private String id;
    @Size(min = 2, max = 80)
    @NotBlank
    private String description;

    @NotBlank
    private OrderStatus status;

    @NotBlank
    @Min(30)
    @Max(36)
    private String customerId;

    @NotBlank
    private List<OrderItem> items;

    public Order() {}
    public Order(OrderStatus status, String customerId, List<OrderItem> items) {
        this.status = status;
        this.customerId = customerId;
        this.items = items;
    }

    public void validate() throws ValidationException {
        ValidationException validation = new ValidationException();
        validation.checkNull(status, "empty status");
        validation.checkNullEmpty(customerId, "empty customerId");
        validation.checkNullEmpty(items, "empty items");
        for (OrderItem item : items) {
            item.validate();

        }
        validation.checkThrow();
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public MonetaryAmount getAmount() {
        MonetaryAmount zero = Monetary.getDefaultAmountFactory().setNumber(0).create();
        return items.stream().map(i -> i.getAmount()).reduce(zero, MonetaryAmount::add);
    }
    public Payment buildPayment() {
        return new Payment(
                customerId, PaymentStatus.SUBMITTED, null, getAmount());
    }
}
