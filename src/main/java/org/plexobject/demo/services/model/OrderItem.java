package org.plexobject.demo.services.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.plexobject.demo.services.exceptions.ValidationException;

import javax.money.MonetaryAmount;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

public class OrderItem {
    private String id = UUID.randomUUID().toString();

    @Size(min = 2, max = 50)
    @NotBlank
    private String name;

    @Min(30)
    @Max(36)
    private String productId;

    @Min(1)
    @Max(100)
    private int quantity;

    @JsonProperty
    @Min(1)
    @Max(100)
    private MonetaryAmount price;

    public OrderItem() {}
    public OrderItem(String name, String productId, int quantity, MonetaryAmount price) {
        this.name = name;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    public void validate() throws ValidationException {
        ValidationException validation = new ValidationException();
        validation.checkNullEmpty(name, "empty name");
        validation.checkNullEmpty(productId, "empty productId");
        validation.checkNullZero(price, "empty price");
        validation.checkThrow();
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public MonetaryAmount getPrice() {
        return price;
    }

    public void setPrice(MonetaryAmount price) {
        this.price = price;
    }

    public MonetaryAmount getAmount() {
        return price.multiply(quantity);
    }
}
