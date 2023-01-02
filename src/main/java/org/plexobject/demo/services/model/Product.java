package org.plexobject.demo.services.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.plexobject.demo.services.exceptions.ValidationException;

import javax.money.MonetaryAmount;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

public class Product {
    private String id = UUID.randomUUID().toString();

    @Size(min = 2, max = 50)
    @NotBlank
    private String name;

    private Category category;

    @Min(1)
    @Max(10000)
    private int inventory;

    @JsonProperty
    @Min(1)
    @Max(100)
    private MonetaryAmount price;

    public Product() {}
    public Product(String name, Category category, int inventory, MonetaryAmount price) {
        this.name = name;
        this.category = category;
        this.inventory = inventory;
        this.price = price;
    }

    public void validate() throws ValidationException {
        ValidationException validation = new ValidationException();
        validation.checkNullEmpty(name, "empty name");
        validation.checkNullZero(inventory, "empty inventory");
        validation.checkNullZero(price, "empty amount");
        validation.checkThrow();
    }


    public MonetaryAmount getPrice() {
        return price;
    }

    public void setPrice(MonetaryAmount price) {
        this.price = price;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }


}