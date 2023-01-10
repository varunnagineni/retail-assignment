package com.retail.rewards.configuration;

import com.retail.rewards.model.Customer;
import com.retail.rewards.model.RewardTransaction;
import com.retail.rewards.repository.CustomerRepository;
import com.retail.rewards.repository.TransactionsRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

@Component
@Slf4j
public class LoadDatabase implements CommandLineRunner {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    TransactionsRepository transactionsRepository;

    @Override
    public void run(String... args) throws Exception {

        //First Customer
        Customer customer = Customer.builder()
                .fName("Var")
                .lName("Nag")
                .emailId("varna@gmail.com")
                .build();
        customerRepository.save(customer);

        RewardTransaction rewardTransaction = RewardTransaction.builder()
                .customer(customer)
                .rewardsEarned(BigDecimal.valueOf(151.02))
                .transStatus("DECLINE")
                .transAmount(BigDecimal.valueOf(150.51))
                .createdDate(getDate(0))
                .build();
        transactionsRepository.save(rewardTransaction);

        rewardTransaction = RewardTransaction.builder()
                .customer(customer)
                .rewardsEarned(BigDecimal.valueOf(25.01))
                .transStatus("APPROVED")
                .transAmount(BigDecimal.valueOf(75.01))
                .createdDate(getDate(0))
                .build();
        transactionsRepository.save(rewardTransaction);

        rewardTransaction = RewardTransaction.builder()
                .customer(customer)
                .rewardsEarned(BigDecimal.valueOf(26.01))
                .transStatus("APPROVED")
                .transAmount(BigDecimal.valueOf(76.01))
                .createdDate(getDate(1))
                .build();
        transactionsRepository.save(rewardTransaction);

        rewardTransaction = RewardTransaction.builder()
                .customer(customer)
                .rewardsEarned(BigDecimal.valueOf(49.99))
                .transStatus("APPROVED")
                .transAmount(BigDecimal.valueOf(99.99))
                .createdDate(getDate(1))
                .build();
        transactionsRepository.save(rewardTransaction);

        rewardTransaction = RewardTransaction.builder()
                .customer(customer)
                .rewardsEarned(BigDecimal.valueOf(49.99))
                .transStatus("APPROVED")
                .transAmount(BigDecimal.valueOf(99.99))
                .createdDate(getDate(2))
                .build();
        transactionsRepository.save(rewardTransaction);

        rewardTransaction = RewardTransaction.builder()
                .customer(customer)
                .rewardsEarned(BigDecimal.valueOf(249.98))
                .transStatus("APPROVED")
                .transAmount(BigDecimal.valueOf(199.99))
                .createdDate(getDate(3))
                .build();
        transactionsRepository.save(rewardTransaction);

        rewardTransaction = RewardTransaction.builder()
                .customer(customer)
                .rewardsEarned(BigDecimal.valueOf(49.99))
                .transStatus("APPROVED")
                .transAmount(BigDecimal.valueOf(99.99))
                .createdDate(getDate(3))
                .build();
        transactionsRepository.save(rewardTransaction);

        rewardTransaction = RewardTransaction.builder()
                .customer(customer)
                .rewardsEarned(BigDecimal.valueOf(0.99))
                .transStatus("APPROVED")
                .transAmount(BigDecimal.valueOf(50.99))
                .createdDate(getDate(3))
                .build();
        transactionsRepository.save(rewardTransaction);

        rewardTransaction = RewardTransaction.builder()
                .customer(customer)
                .rewardsEarned(BigDecimal.valueOf(0.99))
                .transStatus("APPROVED")
                .transAmount(BigDecimal.valueOf(50.99))
                .createdDate(getDate(4))
                .build();
        transactionsRepository.save(rewardTransaction);

        //Second Customer
        customer = Customer.builder()
                .fName("Kum")
                .lName("Nag")
                .emailId("Kumnag@gmail.com")
                .build();
        customerRepository.save(customer);

        rewardTransaction = RewardTransaction.builder()
                .customer(customer)
                .rewardsEarned(BigDecimal.valueOf(0.0))
                .transStatus("APPROVED")
                .transAmount(BigDecimal.valueOf(49.01))
                .createdDate(getDate(0))
                .build();
        transactionsRepository.save(rewardTransaction);

        rewardTransaction = RewardTransaction.builder()
                .customer(customer)
                .rewardsEarned(BigDecimal.valueOf(16.99))
                .transStatus("APPROVED")
                .transAmount(BigDecimal.valueOf(66.99))
                .createdDate(getDate(0))
                .build();
        transactionsRepository.save(rewardTransaction);

        rewardTransaction = RewardTransaction.builder()
                .customer(customer)
                .rewardsEarned(BigDecimal.valueOf(36.01))
                .transStatus("APPROVED")
                .transAmount(BigDecimal.valueOf(86.01))
                .createdDate(getDate(1))
                .build();
        transactionsRepository.save(rewardTransaction);

        rewardTransaction = RewardTransaction.builder()
                .customer(customer)
                .rewardsEarned(BigDecimal.valueOf(39.99))
                .transStatus("APPROVED")
                .transAmount(BigDecimal.valueOf(89.99))
                .createdDate(getDate(1))
                .build();
        transactionsRepository.save(rewardTransaction);

        rewardTransaction = RewardTransaction.builder()
                .customer(customer)
                .rewardsEarned(BigDecimal.valueOf(9.99))
                .transStatus("APPROVED")
                .transAmount(BigDecimal.valueOf(59.99))
                .createdDate(getDate(2))
                .build();
        transactionsRepository.save(rewardTransaction);

        rewardTransaction = RewardTransaction.builder()
                .customer(customer)
                .rewardsEarned(BigDecimal.valueOf(249.98))
                .transStatus("APPROVED")
                .transAmount(BigDecimal.valueOf(199.99))
                .createdDate(getDate(3))
                .build();
        transactionsRepository.save(rewardTransaction);

        rewardTransaction = RewardTransaction.builder()
                .customer(customer)
                .rewardsEarned(BigDecimal.valueOf(27.99))
                .transStatus("APPROVED")
                .transAmount(BigDecimal.valueOf(77.99))
                .createdDate(getDate(3))
                .build();
        transactionsRepository.save(rewardTransaction);

        rewardTransaction = RewardTransaction.builder()
                .customer(customer)
                .rewardsEarned(BigDecimal.valueOf(1.99))
                .transStatus("APPROVED")
                .transAmount(BigDecimal.valueOf(51.99))
                .createdDate(getDate(3))
                .build();
        transactionsRepository.save(rewardTransaction);

        rewardTransaction = RewardTransaction.builder()
                .customer(customer)
                .rewardsEarned(BigDecimal.valueOf(0.99))
                .transStatus("APPROVED")
                .transAmount(BigDecimal.valueOf(50.99))
                .createdDate(getDate(4))
                .build();
        transactionsRepository.save(rewardTransaction);
    }

    private Date getDate(int subractMonth) {

        return DateUtils.addMonths(new Date(), -subractMonth);

    }
}
