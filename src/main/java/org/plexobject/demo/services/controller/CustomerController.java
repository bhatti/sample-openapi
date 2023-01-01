package org.plexobject.demo.services.controller;

import org.plexobject.demo.services.exceptions.CustomerNotFoundException;
import org.plexobject.demo.services.model.Customer;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@RestController
public class CustomerController {

    private final Map<String, Customer> customers = new ConcurrentHashMap<>();

    @PostMapping("/customers")
    public Customer saveCustomer(@Valid @RequestBody Customer customer) {
        customer.setId(UUID.randomUUID().toString());
        customer.validate();
        customers.put(customer.getId(), customer);
        return customer;
    }

    @GetMapping(path = "/customers/:id")
    public Customer getCustomer(@PathVariable(name = "id") @NotBlank @Size(max = 36) String id) {
        Customer customer = customers.get(id);
        if (customer == null) {
            throw new CustomerNotFoundException();
        }
        return customer;
    }

    @GetMapping(path = "/customers")
    public List<Customer> getCustomerByEmail(@RequestParam(name = "email", defaultValue = "", required = false) @NotBlank @Size(max = 36) String email) {
        return customers.values().stream().filter(p -> "".equals(email) || p.getEmail().equals(email)).collect(Collectors.toList());
    }
}

