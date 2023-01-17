package com.retail.rewards;

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
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

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
    public void createCustomers() throws Exception {
        String uri = "/api/customers";
        Customer customer = Customer.builder()
                .fName("Kam")
                .lName("Kum")
                .emailId("varnag@gmail.com")
                .build();

        String postValue = OBJECT_MAPPER.writeValueAsString(Collections.singletonList(customer));
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(postValue))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        List<Customer> results = Arrays.asList(OBJECT_MAPPER.readValue(mvcResult.getResponse().getContentAsString(), Customer[].class));
        custId = Math.toIntExact(results.get(0).getId());
        assertEquals(200, status);
    }

    @Test
    public void getAllCustomers() throws Exception {
        String uri = "/api/customers";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get(uri)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        List<Customer> result = Arrays.asList(OBJECT_MAPPER.readValue(mvcResult.getResponse().getContentAsString(), Customer[].class));
        assertEquals(custId, result.size());
    }

    @Test
    public void getCustomerById() throws Exception {
        String uri = "/api/customer/"+custId;
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get(uri)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        Customer result = OBJECT_MAPPER.readValue(mvcResult.getResponse().getContentAsString(), Customer.class);
        assertEquals("Kam", result.getFName());
    }

    @Test
    public void updateCustomer() throws Exception {
        String uri = "/api/customer";
        Customer customer = Customer.builder()
                .id((long) custId)
                .fName("Kam")
                .lName("Kuma")
                .emailId("varnag@gmail.com")
                .build();

        String postValue = OBJECT_MAPPER.writeValueAsString(customer);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .put(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(postValue))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        Customer result = OBJECT_MAPPER.readValue(mvcResult.getResponse().getContentAsString(), Customer.class);
        assertEquals(200, status);
        assertEquals(customer.getLName(), result.getLName());
    }

    @Test
    public void createRewardTransaction() throws Exception {
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

        String postValue = OBJECT_MAPPER.writeValueAsString(rewardTransaction);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(postValue))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        RewardTransaction result = OBJECT_MAPPER.readValue(mvcResult.getResponse().getContentAsString(), RewardTransaction.class);
        transId = Math.toIntExact(result.getId());
        assertEquals(200, status);
        assertEquals(BigDecimal.valueOf(151.02), result.getRewardsEarned());
    }

    @Test
    public void updateRewardTransaction() throws Exception {
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

        String postValue = OBJECT_MAPPER.writeValueAsString(rewardTransaction);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .put(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(postValue))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        RewardTransaction result = OBJECT_MAPPER.readValue(mvcResult.getResponse().getContentAsString(), RewardTransaction.class);
        assertEquals(200, status);
        assertEquals(rewardTransaction.getTransStatus(), result.getTransStatus());
    }

    private Date getDate(int subractMonth) {

        return DateUtils.addMonths(new Date(), -subractMonth);
    }

}
