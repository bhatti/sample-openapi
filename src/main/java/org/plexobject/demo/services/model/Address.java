package org.plexobject.demo.services.model;

import org.plexobject.demo.services.exceptions.ValidationException;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class Address {
    private String id;
    @Size(min = 2, max = 70)
    @NotBlank
    private String streetAddress;
    @Size(min = 2, max = 60)
    @Pattern(regexp = "\\w+", message = "Please provide a valid street address")
    @NotBlank
    private String city;
    @Size(min = 2, max = 12)
    @NotBlank
    @Pattern(regexp = "\\d{5}", message = "Please provide a valid zip code")
    private String zipCode;
    private CountryCode countryCode;

    public Address() {
    }

    public Address(String streetAddress, String city, String zipCode, CountryCode countryCode) {
        this.streetAddress = streetAddress;
        this.city = city;
        this.zipCode = zipCode;
        this.countryCode = countryCode;
    }

    public void validate() throws ValidationException {
        ValidationException validation = new ValidationException();
        validation.checkNullEmpty(streetAddress, "empty streetAddress");
        validation.checkNullEmpty(city, "empty city");
        validation.checkNullEmpty(zipCode, "empty zipCode");
        validation.checkNull(countryCode, "empty countryCode");
        validation.checkThrow();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public CountryCode getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(CountryCode countryCode) {
        this.countryCode = countryCode;
    }
}
