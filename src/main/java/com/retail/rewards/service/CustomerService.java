package com.retail.rewards.service;

import com.retail.rewards.model.Customer;
import com.retail.rewards.repository.CustomerRepository;
import com.retail.rewards.util.Constants;
import com.retail.rewards.util.ServiceUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private static final Logger log = LogManager.getLogger(CustomerService.class);

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ServiceUtil serviceUtil;

    public Customer createCustomer(Customer customer) {
        if (!serviceUtil.isEmailAddressValid(customer.getEmailId()))
            throw new IllegalArgumentException("Email ID is not valid");
        return customerRepository.save(customer);
    }

    public List<Customer> createCustomers(List<Customer> customers) {
        return customerRepository.saveAll(customers);
    }

    public Customer getCustomerById(long id) {
        return customerRepository.findById(id).orElse(null);
    }

    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    public Customer updateCustomerSubscription(Customer customer) {

        Customer cust = customerRepository
                .findById(customer.getId()).orElse(null);
        if(cust == null) {
            return null;
        }

        List<String> subscriptionList = null;
        if (customer.getAction().equals(Constants.ENROLL_TO_THE_PROGRAM)) {
            if (StringUtils.isBlank(customer.getSubscriptions())) {
                cust.setSubscriptions(customer.getSubscriptions().toLowerCase().trim());
            } else {
                subscriptionList
                        = serviceUtil.getTokensWithCollection(cust.getSubscriptions().trim());
                if (!subscriptionList.contains(customer.getSubscriptions().toLowerCase().trim()))
                    subscriptionList.add(customer.getSubscriptions().toLowerCase().trim());
            }
        }

        if (customer.getAction().equals(Constants.REMOVE_TO_THE_PROGRAM)) {
            if (StringUtils.isNotBlank(customer.getSubscriptions())) {
                subscriptionList
                        = serviceUtil.getTokensWithCollection(cust.getSubscriptions().trim());
                subscriptionList.remove(customer.getSubscriptions().toLowerCase().trim());
            }
        }

        assert subscriptionList != null;
        cust.setSubscriptions(subscriptionList.stream().map(String::toLowerCase)
                .collect(Collectors.joining(",")));
        return customerRepository.save(cust);
    }

    public CustomerService(CustomerRepository customerRepository, ServiceUtil serviceUtil) {
        this.customerRepository = customerRepository;
        this.serviceUtil = serviceUtil;
    }
}
