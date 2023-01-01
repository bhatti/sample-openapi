package org.plexobject.demo.services.model;

import org.plexobject.demo.services.exceptions.ValidationException;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class Customer {
    private String id;

    @Size(min = 2, max = 50)
    @Pattern(regexp = "\\w", message = "Please provide a valid first name")
    @NotBlank
    private String firstName;

    @Size(min = 2, max = 50)
    @Pattern(regexp = "\\w", message = "Please provide a valid last name")
    @NotBlank
    private String lastName;

    @Pattern(regexp = ".+@.+\\..+", message = "Please provide a valid email address")
    private String email;

    @NotBlank
    @Pattern(regexp = "1-\\d{3}-\\d{4}-\\d{4}", message = "Please provide a valid phone")
    private String phone;

    @NotBlank
    private CreditCard creditCard;
    @NotBlank
    private Address address;

    public Customer() {
    }

    public Customer(String firstName, String lastName, String email, String phone, CreditCard creditCard, Address address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.creditCard = creditCard;
        this.address = address;
    }

    public void validate() throws ValidationException {
        ValidationException validation = new ValidationException();
        validation.checkNullEmpty(firstName, "empty firstName");
        validation.checkNullEmpty(lastName, "empty lastName");
        validation.checkNullEmpty(email, "empty email");
        validation.checkNullEmpty(phone, "empty phone");
        validation.checkNull(creditCard, "empty credit card");
        validation.checkNull(address, "empty address");
        creditCard.validate();
        address.validate();
        validation.checkThrow();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
