package com.retail.rewards;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.retail.rewards.model.Customer;
import com.retail.rewards.model.RewardTransaction;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RetailAssignmentApplication.class)
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RetailAssignmentApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper OBJECT_MAPPER;

    private static int custId;

    private static int transId;

    @Test
    public void GivenCreateCustomers_WhenCustomersAreValid_Then201StatusCodeIsReturned() throws Exception {
        String uri = "/api/customers";
        Customer customer = Customer.builder()
                .fName("Kam")
                .lName("Kum")
                .emailId("varnag@gmail.com")
                .build();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(Collections.singletonList(customer))))
                .andExpect(status().isCreated())
               .andReturn();

        List<Customer> results = OBJECT_MAPPER.readValue(result.getResponse().getContentAsString(), new TypeReference<List<Customer>>() {});
        custId = Math.toIntExact(results.get(0).getId());
    }

    @Test
    public void GivenCreateCustomers_WhenCustomersAreInValid_ThenBadRequestIsReturned() throws Exception {
        String uri = "/api/customers";
        Customer customer = Customer.builder()
                .fName("Kam")
                .lName("Kum")
                .emailId("")
                .build();

        mockMvc.perform(MockMvcRequestBuilders
                        .post(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(Collections.singletonList(customer))))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void GivenGetCustomerById_WhenValidCustIdIsValid_Then200StatusCodeAndCustomerIsReturned() throws Exception {
        String uri = "/api/customer/"+custId;
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get(uri)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        Customer result = OBJECT_MAPPER.readValue(mvcResult.getResponse().getContentAsString(), Customer.class);
        assertEquals("Kam", result.getFName());
    }

    @Test
    public void GivenGetCustomerById_WhenValidCustIdIsInValid_Then404StatusCodeAndCustomerIsReturned() throws Exception {
        String uri = "/api/customer/"+20L;
        mockMvc.perform(MockMvcRequestBuilders
                        .get(uri)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void GivenUpdateCustomerSubscription_WhenValidCustomerData_Then200StatusCodeAndUpdatedCustomerIsReturned() throws Exception {
        String uri = "/api/customer";
        Customer customer = Customer.builder()
                .id((long) custId)
                .fName("Kam")
                .lName("Kuma")
                .emailId("varnag@gmail.com")
                .build();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .put(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(customer)))
                .andExpect(status().isOk())
                .andReturn();
        Customer result = OBJECT_MAPPER.readValue(mvcResult.getResponse().getContentAsString(), Customer.class);
        assertEquals(customer.getLName(), result.getLName());
    }

    @Test
    public void GivenUpdateCustomerSubscription_WhenInValidCustomerId_Then404StatusCodeAndUpdatedCustomerIsReturned() throws Exception {
        String uri = "/api/customer";
        Customer customer = Customer.builder()
                .id((long) 105L)
                .fName("Kam")
                .lName("Kuma")
                .emailId("varnag@gmail.com")
                .build();

        mockMvc.perform(MockMvcRequestBuilders
                        .put(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(customer)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void GivenCreateRewardTransaction_WhenRewardTransactionIsValid_Then201StatusCodeIsReturned() throws Exception {
        String uri = "/api/transaction";
        Customer customer = Customer.builder()
                .id((long) custId)
                .fName("Kam")
                .lName("Kum")
                .emailId("varnag@gmail.com")
                .build();

        RewardTransaction rewardTransaction = RewardTransaction.builder()
                .customer(customer)
                .transStatus("APPROVED")
                .transAmount(BigDecimal.valueOf(150.51))
                .createdDate(getDate(0))
                .build();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(rewardTransaction)))
                .andExpect(status().isCreated())
                .andReturn();
        RewardTransaction result = OBJECT_MAPPER.readValue(mvcResult.getResponse().getContentAsString(), RewardTransaction.class);
        transId = Math.toIntExact(result.getId());
        assertEquals(BigDecimal.valueOf(151.02), result.getRewardsEarned());
    }

    @Test
    public void GivenCreateRewardTransaction_WhenRewardTransactionIsInValid_ThenBadRequestIsReturned() throws Exception {
        String uri = "/api/transaction";
        Customer customer = Customer.builder()
                .id((long) custId)
                .fName("Kam")
                .lName("Kum")
                .emailId("varnag@gmail.com")
                .build();

        RewardTransaction rewardTransaction = RewardTransaction.builder()
                .customer(customer)
                .transStatus("")
                .transAmount(BigDecimal.valueOf(150.51))
                .createdDate(getDate(0))
                .build();

        mockMvc.perform(MockMvcRequestBuilders
                        .post(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(rewardTransaction)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void GivenUpdatedCustomerRewardTransaction_WhenCustomerIdIsValid_Then200StatusCodeIsReturned() throws Exception {
        String uri = "/api/transaction";
        Customer customer = Customer.builder()
                .id((long) custId)
                .fName("Kam")
                .lName("Kum")
                .emailId("varnag@gmail.com")
                .build();

        RewardTransaction rewardTransaction = RewardTransaction.builder()
                .id((long) transId)
                .customer(customer)
                .transStatus("DECLINE")
                .transAmount(BigDecimal.valueOf(150.51))
                .createdDate(getDate(0))
                .build();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .put(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(rewardTransaction)))
                .andExpect(status().isOk())
                .andReturn();
        RewardTransaction result = OBJECT_MAPPER.readValue(mvcResult.getResponse().getContentAsString(), RewardTransaction.class);
        assertEquals(rewardTransaction.getTransStatus(), result.getTransStatus());
    }

    @Test
    public void GivenUpdatedCustomerRewardTransaction_WhenTransactionIdIsInValid_Then404StatusCodeIsReturned() throws Exception {
        String uri = "/api/transaction";
        Customer customer = Customer.builder()
                .id((long) custId)
                .fName("Kam")
                .lName("Kum")
                .emailId("varnag@gmail.com")
                .build();

        RewardTransaction rewardTransaction = RewardTransaction.builder()
                .id(100L)
                .customer(customer)
                .transStatus("DECLINE")
                .transAmount(BigDecimal.valueOf(150.51))
                .createdDate(getDate(0))
                .build();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .put(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(rewardTransaction)))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void GivenGetRewardSummary_WhenValidCustIdIsInValid_Then404StatusCodeAndCustomerIsReturned() throws Exception {
        String uri = "/api/summary/"+20L;
        mockMvc.perform(MockMvcRequestBuilders
                        .get(uri)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    private Date getDate(int subractMonth) {

        return DateUtils.addMonths(new Date(), -subractMonth);
    }

}
