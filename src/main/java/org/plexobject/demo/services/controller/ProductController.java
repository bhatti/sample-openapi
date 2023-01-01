package org.plexobject.demo.services.controller;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.plexobject.demo.services.exceptions.ProductInventoryNotAvailable;
import org.plexobject.demo.services.exceptions.ProductNotFoundException;
import org.plexobject.demo.services.model.Product;

import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {

	private final Map<String, Product> products = new ConcurrentHashMap<>();

	@PostMapping("/products")
	public Product saveProduct(@Valid @RequestBody Product product) {
		product.setId(UUID.randomUUID().toString());
		products.put(product.getId(), product);
		return product;
	}

	@GetMapping(path = "/products/:id")
	public Product getProduct(@PathVariable(name = "id")  @NotBlank @Size(max = 36) String id) {
		Product product = products.get(id);
		if (product == null) {
			throw new ProductNotFoundException();
		}
		return product;
	}

	@PostMapping(path = "/products/:id/updateQuantity/:quantity")
	public Product updateProductQuantity(
			@PathVariable(name = "id")  @NotBlank @Size(max = 36) String id,
			@PathVariable(name="quantity")  @NotBlank int quantity) {

		Product product = products.get(id);
		if (product == null) {
			throw new ProductNotFoundException();
		}
		if (quantity < 0 && product.getInventory() < quantity) {
			throw new ProductInventoryNotAvailable();
		}
		product.setInventory(product.getInventory() + quantity);
		return product;
	}

	@GetMapping(path = "/products")
	public List<Product> getProductByCategory(@RequestParam(name = "category", defaultValue = "") @Size(max = 36) String category) {
		return products.values().stream().filter(p -> "".equals(category) || p.getCategory().equals(category)).collect(Collectors.toList());
	}
}
