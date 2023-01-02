package org.plexobject.demo.services.controller;

import org.plexobject.demo.services.exceptions.OrderNotFoundException;
import org.plexobject.demo.services.model.Customer;
import org.plexobject.demo.services.model.Order;
import org.plexobject.demo.services.model.OrderItem;
import org.plexobject.demo.services.model.Payment;
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
public class OrderController {

    private final Map<String, Order> orders = new ConcurrentHashMap<>();
    private final ProductController productController = new ProductController();
    private final PaymentController paymentController = new PaymentController();

    private final CustomerController customerController = new CustomerController();

    @PostMapping("/orders")
    public Order submitOrder(@Valid @RequestBody Order order) {
        // no rollback/compensating transactions
        order.setId(UUID.randomUUID().toString());
        order.validate();
        Customer customer =  customerController.getCustomer(order.getCustomerId());
        Payment payment = paymentController.charge(customer.getCreditCard().getCardNumber(), order.getAmount());
        for (OrderItem item: order.getItems()) {
            productController.updateProductQuantity(item.getId(), item.getQuantity());
        }

        orders.put(order.getId(), order);
        return order;
    }

    @GetMapping(path = "/orders/{id}")
    public Order getOrder(@PathVariable(name = "id") @NotBlank @Size(max = 36) String id) {
        Order order = orders.get(id);
        if (order == null) {
            throw new OrderNotFoundException();
        }
        return order;
    }

    @GetMapping(path = "/orders")
    public List<Order> getOrderByCustomer(@RequestParam(name = "id", defaultValue = "") @Size(max = 36) String id) {
        return orders.values().stream().filter(p -> "".equals(id) || p.getCustomerId().equals(id)).collect(Collectors.toList());
    }
}

