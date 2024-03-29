package com.retail.rewards.service;

import com.retail.rewards.model.Customer;
import com.retail.rewards.repository.CustomerRepository;
import com.retail.rewards.util.Constants;
import com.retail.rewards.util.ServiceUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

public class CustomerServiceTest {

    @InjectMocks
    CustomerService customerService;

    @Mock
    CustomerRepository customerRepositoryMock;

    @Mock
    ServiceUtil serviceUtilMock;

    @Before
    public void setUp() {
        serviceUtilMock = mock(ServiceUtil.class);
        customerRepositoryMock = mock(CustomerRepository.class);
        customerService = new CustomerService();
        customerService.customerRepository = customerRepositoryMock;
        customerService.serviceUtil = serviceUtilMock;
    }

    @Test
    public void GivenValidCustomersList_WhenCreateCustomersIsCalled_ThenCreatedCustomersListAreReturned() {

        Customer customer = createDTO(null, "Nag", "Kum", "Nagkum@gmail.com");
        Customer created = createObject(5L, "Nag", "Kum", "Nagkum@gmail.com");

        when(customerRepositoryMock.saveAll(anyList())).thenReturn(Collections.singletonList(created));

        List<Customer> cust = customerService.createCustomers(Collections.singletonList(customer));

        ArgumentCaptor<List> customerArgumentCaptor = ArgumentCaptor.forClass(List.class);
        verify(customerRepositoryMock, times(1)).saveAll(customerArgumentCaptor.capture());

        assertEquals(Collections.singletonList(created).size(), cust.size());
    }

    @Test
    public void GivenValidCustomerId_WhenGetCustomerByIdIsCalled_ThenValidCustomerIsReturned() {
        Long id = 5L;
        Customer created = createObject(id, "Nag", "Kum", "Nagkum@gmail.com");
        when(customerRepositoryMock.findById(id)).thenReturn(Optional.of(created));

        Customer cust = customerService.getCustomerById(id);

        verify(customerRepositoryMock, times(1)).findById(id);

        assertEquals(created, cust);
    }

    @Test
    public void GivenValidCustomerData_WhenUpdateCustomerSubscriptionIsCalled_ThenCustomerIsEnrolledToTheSubscription() {

        String rewards = "reward, retail, customer";
        String updatedRewards = "reward, retail, customer, transaction";
        Long id = 5L;

        Customer customer = createDTO(id, "Nag", "Kum", "Nagkum@gmail.com");
        customer.setSubscriptions(rewards);
        Customer created = createObject(id, "Nag", "Kum", "Nagkum@gmail.com");
        created.setSubscriptions(updatedRewards);

        when(customerRepositoryMock.findById(id)).thenReturn(Optional.of(customer));
        Customer cust = Customer.builder()
                .id(id)
                .lName("Nag")
                .lName("Kum")
                .subscriptions("transactions")
                .action(Constants.ENROLL_TO_THE_PROGRAM)
                .build();

        when(customerRepositoryMock.save(any(Customer.class))).thenReturn(created);

        Customer customer1 = customerService.updateCustomerSubscription(cust);

        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerRepositoryMock, times(1)).save(customerArgumentCaptor.capture());
        verify(customerRepositoryMock, times(1)).findById(id);

        assertEquals(created.getSubscriptions(), customer1.getSubscriptions());
    }

    @Test
    public void GivenUnknownCustomerData_WhenUpdateCustomerSubscriptionIsCalled_ThenNullIsReturned() {

        Long id = 5L;

        when(customerRepositoryMock.findById(id)).thenReturn(Optional.empty());
        Customer cust = Customer.builder()
                .id(id)
                .lName("Nag")
                .lName("Kum")
                .subscriptions("transactions")
                .action(Constants.ENROLL_TO_THE_PROGRAM)
                .build();

        when(customerRepositoryMock.save(any(Customer.class))).thenReturn(cust);

        Customer customer1 = customerService.updateCustomerSubscription(cust);

        verify(customerRepositoryMock, times(0)).save(cust);
        verify(customerRepositoryMock, times(1)).findById(id);

        assertNull(customer1);
    }

    @Test
    public void GivenValidCustomerData_WhenUpdateCustomerSubscriptionIsCalled_ThenCustomerIsRemovedToTheSubscription() {

        String updatedRewards = "reward, retail, customer";
        String rewards = "reward, retail, customer, transaction";
        Long id = 5L;

        Customer customer = createDTO(id, "Nag", "Kum", "Nagkum@gmail.com");
        customer.setSubscriptions(rewards);
        Customer created = createObject(id, "Nag", "Kum", "Nagkum@gmail.com");
        created.setSubscriptions(updatedRewards);

        when(customerRepositoryMock.findById(id)).thenReturn(Optional.of(customer));
        Customer cust = Customer.builder()
                .id(id)
                .lName("Nag")
                .lName("Kum")
                .subscriptions("transactions")
                .action(Constants.REMOVE_TO_THE_PROGRAM)
                .build();

        when(customerRepositoryMock.save(any(Customer.class))).thenReturn(created);

        Customer customer1 = customerService.updateCustomerSubscription(cust);

        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerRepositoryMock, times(1)).save(customerArgumentCaptor.capture());
        verify(customerRepositoryMock, times(1)).findById(id);

        assertEquals(created.getSubscriptions(), customer1.getSubscriptions());
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
