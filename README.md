# Getting Started
    This is a maven based Spring boot restful service.
    Below are the API's available in this application:

    Controllers (com.retail.rewards.controller) :
        CustomerRewardController:
          a) createCustomer (Accepts Customer Object and returns Customer object along with cust_id)
          b) createCustomers (Accepts list of Customer objects and returns list of Customer objects)
          c) getCustomerById (Accepts cust_Id and returns Customer Object)
          d) getAllCustomers (Returns list of Customer objects)
          e) updateCustomerSubscription (Action attribute in Customer object
                accepts REMOVE or ENROLL) (Accepts Customer with custId and updates based on the action)
    
        TransactionRewardsController:
            a) createRewardTransaction (Accepts RewardTransactions and returns RewardTransactions)
            b) updateRewardTransaction (transStatus attribute accepts either 
                    APPROVED or DECLINE) 
            c) getRewardSummaryByCustId (Get reward summary for the given custId)

    Models/Entities (com.retail.rewards.model)
        a) Customer
        b) RewardTransactions
        c) CustomerRewardSummary (Interface)

    Services (com.retail.rewards.service):
        a) CustomerService
        b) TransactionRewardService

    Configuration (com.retail.rewards.configuration) :
        a) LoadDatabase.java - Load data to H2 database
        b) SpringFoxConfig - Swagger configurations.

### Software versions:
    a) JDK 1.8
    b) Maven 3.6.3
    c) Used H2 inmemory database

### To Get the project from Git:
    git clone 

### Steps to run the application locally:

    a) Execute mvn -U clean install
    b) Run RetailAssignmentApplication class to start the spring boot app.
    c) Swagger URL: http://localhost:8080/swagger-ui/index.html
    d) For checking health status: http://localhost:8080/actuator/health
    e) h2 url: http://localhost:8080/h2-ui

### Testing

    a) Can test all the above API's using Swagger URL.

