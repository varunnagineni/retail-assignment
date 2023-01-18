package com.retail.rewards.controller;

import com.retail.rewards.model.Customer;
import com.retail.rewards.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CustomerRewardController {

    @Autowired
    CustomerService customerService;

    @PostMapping("/customers")
    public ResponseEntity<List<Customer>> createCustomers(@RequestBody List<Customer> customers) {
        List<Customer> createdCustomers = customerService.createCustomers(customers);
        if (createdCustomers == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(createdCustomers, HttpStatus.CREATED);
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        Customer customer = customerService.getCustomerById(id);
        if (customer == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @PutMapping("/customer")
    public ResponseEntity<Customer> updateCustomerSubscription(@RequestBody Customer customer) {

        Customer updatedCustomer = customerService.updateCustomerSubscription(customer);
        if (updatedCustomer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
    }

}
