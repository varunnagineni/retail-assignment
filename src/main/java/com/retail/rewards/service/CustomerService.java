package com.retail.rewards.service;

import com.retail.rewards.model.Customer;
import com.retail.rewards.repository.CustomerRepository;
import com.retail.rewards.util.Constants;
import com.retail.rewards.util.ServiceUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ServiceUtil serviceUtil;

    /**
     * Create customers in bulk
     * @param customers - Customer objects will be passed without custId's
     * @return - returns list of customers
     */
    public List<Customer> createCustomers(List<Customer> customers) {
        return customerRepository.saveAll(customers);
    }

    /**
     *
     * @param id - customerID is passed
     * @return - Customer if present or else null
     */
    public Customer getCustomerById(long id) {
        return customerRepository.findById(id).orElse(null);
    }

    /**
     *
     * @return - Gets all the Customers in customer table
     */
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    /**
     * This method just Adds or removes the customer subscription.
     * If action is ENROLL, we will enroll the customer with the provided subscription
     * If action is REMOVE, we will remove the customer with the provided subscription
     * @param customer - Accepts the customer that needs to be updated
     * @return - Updated customer will be returned
     */
    public Customer updateCustomerSubscription(Customer customer) {

        Customer cust = customerRepository
                .findById(customer.getId()).orElse(null);
        if(cust == null) {
            return null;
        }

        List<String> subscriptionList = null;
        if (StringUtils.isNotBlank(customer.getAction())) {
            if (customer.getAction().equalsIgnoreCase(Constants.ENROLL_TO_THE_PROGRAM)) {
                if (StringUtils.isNotBlank(customer.getSubscriptions())) {
                    subscriptionList
                            = serviceUtil.getTokensWithCollection(cust.getSubscriptions().trim());
                    if (!subscriptionList.contains(customer.getSubscriptions().toLowerCase().trim()))
                        subscriptionList.add(customer.getSubscriptions().toLowerCase().trim());
                }
            }

            if (customer.getAction().equalsIgnoreCase(Constants.REMOVE_TO_THE_PROGRAM)) {
                if (StringUtils.isNotBlank(customer.getSubscriptions())) {
                    subscriptionList
                            = serviceUtil.getTokensWithCollection(cust.getSubscriptions().trim());
                    subscriptionList.remove(customer.getSubscriptions().toLowerCase().trim());
                }
            }
        }

        if (subscriptionList != null) {
            customer.setSubscriptions(subscriptionList.stream().map(String::toLowerCase)
                    .collect(Collectors.joining(Constants.DELIMITER)));
        }
        return customerRepository.save(customer);
    }
}
