package com.retail.rewards.controller;

import com.retail.rewards.model.Customer;
import com.retail.rewards.service.CustomerService;
import com.retail.rewards.util.Constants;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

public class CustomerRewardControllerTest {

    @InjectMocks
    CustomerRewardController customerRewardController;

    @Mock
    CustomerService customerServiceMock;

    @Before
    public void setUp() {
        customerRewardController = new CustomerRewardController();
        customerServiceMock = Mockito.mock(CustomerService.class);
        customerRewardController.customerService = customerServiceMock;
    }

    @Test
    public void createCustomer_success() {
        Customer customer = createDTO(null, "Nag", "Kum", "Nagkum@gmail.com");
        Customer created = createObject(Long.valueOf(5), "Nag", "Kum", "Nagkum@gmail.com");

        Mockito.when(customerServiceMock.createCustomer(customer)).thenReturn(created);
        Customer cust = customerRewardController.createCustomer(customer);
        Mockito.verify(customerServiceMock, Mockito.times(1)).createCustomer(customer);
        Assert.assertEquals(created, cust);
    }

    @Test
    public void createCustomer_Exception() {
        IllegalArgumentException exp = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            customerRewardController.createCustomer(null);
        });
        Assert.assertEquals("customer object can not be null", exp.getMessage());
    }

    @Test
    public void createCustomers_success() {
        List<Customer> customers = Arrays.asList(createDTO(null, "Nag", "Kum", "Nagkum@gmail.com"));
        List<Customer> created = Arrays.asList(createObject(Long.valueOf(5), "Nag", "Kum", "Nagkum@gmail.com"));

        Mockito.when(customerServiceMock.createCustomers(customers)).thenReturn(created);
        List<Customer> cust = customerRewardController.createCustomers(customers);
        Mockito.verify(customerServiceMock, Mockito.times(1)).createCustomers(customers);
        Assert.assertEquals(created.get(0), cust.get(0));
    }

    @Test
    public void createCustomers_Exception() {
        List<Customer> customers = null;
        IllegalArgumentException exp = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            customerRewardController.createCustomers(customers);
        });
        Assert.assertEquals("customers objects can not be null", exp.getMessage());
    }

    @Test
    public void getCustomerById_Success() {
        Long id = Long.valueOf(5);
        Customer created = createObject(id, "Nag", "Kum", "Nagkum@gmail.com");
        Mockito.when(customerServiceMock.getCustomerById(id)).thenReturn(created);
        Customer cust = customerRewardController.getCustomerById(id);
        Mockito.verify(customerServiceMock, Mockito.times(1)).getCustomerById(id);
        Assert.assertNotNull(cust);
    }

    @Test
    public void getAllCustomers_Success() {
        Long id = Long.valueOf(5);
        List<Customer> created = Arrays.asList(createObject(id, "Nag", "Kum", "Nagkum@gmail.com"));
        Mockito.when(customerServiceMock.getCustomers()).thenReturn(created);
        List<Customer> cust = customerRewardController.getAllCustomers();
        Mockito.verify(customerServiceMock, Mockito.times(1)).getCustomers();
        Assert.assertNotNull(cust);
        Assert.assertEquals(cust.size(), 1);
    }

    @Test
    public void updateCustomer_Exception() {
        IllegalArgumentException exp = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            customerRewardController.updateCustomerSubscription(null);
        });
        Assert.assertEquals("customer object can not be null", exp.getMessage());
    }

    @Test
    public void updateCustomer_Success() {
        String rewards = "reward, retail, customer";
        String updatedRewards = "reward, retail, customer, transaction";
        Long id = Long.valueOf(5);

        Customer customer = createDTO(id, "Nag", "Kum", "Nagkum@gmail.com");
        customer.setSubscriptions(rewards);
        Customer created = createObject(id, "Nag", "Kum", "Nagkum@gmail.com");
        created.setSubscriptions(updatedRewards);

        Customer cust = Customer.builder()
                .id(id)
                .lName("Nag")
                .lName("Kum")
                .subscriptions("transactions")
                .action(Constants.ENROLL_TO_THE_PROGRAM)
                .build();

        Mockito.when(customerServiceMock.updateCustomerSubscription(cust)).thenReturn(created);
        Customer customer1 = customerRewardController.updateCustomerSubscription(customer);

        Mockito.verify(customerServiceMock, Mockito.times(1)).updateCustomerSubscription(customer);
    }

    private Customer createDTO(Long id, String firstName, String lastName, String emailId) {
        return Customer.builder()
                .id(id)
                .lName(firstName)
                .fName(lastName)
                .emailId(emailId)
                .build();
    }

    private Customer createObject (Long id, String firstName, String lastName, String emailId) {
        Customer model = Customer.builder()
                .lName(firstName)
                .fName(lastName)
                .emailId(emailId)
                .build();
        model.setId(id);

        return model;
    }
}
