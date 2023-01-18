package com.retail.rewards.controller;

import com.retail.rewards.model.Customer;
import com.retail.rewards.service.CustomerService;
import com.retail.rewards.util.Constants;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CustomerRewardControllerTest {

    @InjectMocks
    CustomerRewardController customerRewardController;

    @Mock
    CustomerService customerServiceMock;

    @Before
    public void setUp() {
        customerRewardController = new CustomerRewardController();
        customerServiceMock = mock(CustomerService.class);
        customerRewardController.customerService = customerServiceMock;
    }

    @Test
    public void GivenValidCustomersData_WhenCreateCustomersIsCalled_Then201StatusCodeIsReturned() {
        List<Customer> customers = Collections.singletonList(createDTO(null, "Nag", "Kum", "Nagkum@gmail.com"));
        List<Customer> created = Collections.singletonList(createObject(5L, "Nag", "Kum", "Nagkum@gmail.com"));

        when(customerServiceMock.createCustomers(customers)).thenReturn(created);
        ResponseEntity<List<Customer>> cust = customerRewardController.createCustomers(customers);
        verify(customerServiceMock, times(1)).createCustomers(customers);
        assertEquals(HttpStatus.CREATED, cust.getStatusCode());
        assertEquals(created, cust.getBody());
    }

    @Test
    public void GivenInappropriateCustomersData_WhenCreateCustomersIsCalled_ThenBadRequestIsReturned() {
        List<Customer> customers = Collections.singletonList(createDTO(null, "", "Kum", "Nagkum@gmail.com"));

        when(customerServiceMock.createCustomers(customers)).thenReturn(null);

        ResponseEntity<List<Customer>> response = customerRewardController.createCustomers(customers);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void GivenValidCustomerId_WhenGetCustomerByIdIsCalled_Then200StatusCodeAndCustomerDetailsAreReturned() {
        long id = 5L;
        Customer created = createObject(id, "Nag", "Kum", "Nagkum@gmail.com");
        when(customerServiceMock.getCustomerById(id)).thenReturn(created);
        ResponseEntity<Customer> cust = customerRewardController.getCustomerById(id);
        verify(customerServiceMock, times(1)).getCustomerById(id);
        assertEquals(HttpStatus.OK, cust.getStatusCode());
        assertNotNull(cust);
    }

    @Test
    public void GivenInValidCustomerId_WhenUpdateCustomerSubscriptionIsCalled_Then404StatusCodeIsReturned() {
        long id = 15L;
        when(customerServiceMock.getCustomerById(id)).thenReturn(null);
        ResponseEntity<Customer> cust = customerRewardController.getCustomerById(id);
        verify(customerServiceMock, times(1)).getCustomerById(id);
        assertEquals(HttpStatus.NOT_FOUND, cust.getStatusCode());
        assertNull(cust.getBody());
    }

    @Test
    public void GivenValidCustomerData_WhenUpdateCustomerSubscriptionIsCalled_Then200StatusCodeAndCustomerIsUpdated() {
        String updatedRewards = "reward, retail, customer, transaction";
        Long id = 5L;

        Customer created = createObject(id, "Nag", "Kum", "Nagkum@gmail.com");
        created.setSubscriptions(updatedRewards);

        Customer cust = Customer.builder()
                .id(id)
                .lName("Nag")
                .lName("Kum")
                .subscriptions("transactions")
                .action(Constants.ENROLL_TO_THE_PROGRAM)
                .build();

        when(customerServiceMock.updateCustomerSubscription(cust)).thenReturn(created);
        ResponseEntity<Customer> updatedCustomerSummary = customerRewardController.updateCustomerSubscription(cust);

        verify(customerServiceMock, times(1)).updateCustomerSubscription(cust);
        assertEquals(HttpStatus.OK, updatedCustomerSummary.getStatusCode());
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
