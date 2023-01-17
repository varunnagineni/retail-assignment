package com.retail.rewards.controller;

import com.retail.rewards.model.Customer;
import com.retail.rewards.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CustomerRewardController {

    @Autowired
    CustomerService customerService;

    @PostMapping("/customers")
    public List<Customer> createCustomers(@RequestBody List<Customer> customers) {
        if (customers == null || customers.size() < 1) {
            throw new IllegalArgumentException("customers objects can not be null");
        }
        return customerService.createCustomers(customers);
    }

    @GetMapping("/customer/{id}")
    public Customer getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }

    @PutMapping("/customer")
    public Customer updateCustomerSubscription(@RequestBody Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("customer object can not be null");
        }
        return customerService.updateCustomerSubscription(customer);
    }

}
