package com.retail.rewards.configuration;

import com.retail.rewards.model.Customer;
import com.retail.rewards.model.RewardTransactions;
import com.retail.rewards.repository.CustomerRepository;
import com.retail.rewards.repository.TransactionsRepository;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class LoadDatabase implements CommandLineRunner {

    private static final Logger log = LogManager.getLogger(LoadDatabase.class);

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

        RewardTransactions rewardTransactions = RewardTransactions.builder()
                .customer(customer)
                .rewardsEarned(151.02)
                .transStatus("DECLINE")
                .transAmount(150.51)
                .createdDate(getDate(0))
                .build();
        transactionsRepository.save(rewardTransactions);

        rewardTransactions = RewardTransactions.builder()
                .customer(customer)
                .rewardsEarned(25.01)
                .transStatus("APPROVED")
                .transAmount(75.01)
                .createdDate(getDate(0))
                .build();
        transactionsRepository.save(rewardTransactions);

        rewardTransactions = RewardTransactions.builder()
                .customer(customer)
                .rewardsEarned(26.01)
                .transStatus("APPROVED")
                .transAmount(76.01)
                .createdDate(getDate(1))
                .build();
        transactionsRepository.save(rewardTransactions);

        rewardTransactions = RewardTransactions.builder()
                .customer(customer)
                .rewardsEarned(49.99)
                .transStatus("APPROVED")
                .transAmount(99.99)
                .createdDate(getDate(1))
                .build();
        transactionsRepository.save(rewardTransactions);

        rewardTransactions = RewardTransactions.builder()
                .customer(customer)
                .rewardsEarned(49.99)
                .transStatus("APPROVED")
                .transAmount(99.99)
                .createdDate(getDate(2))
                .build();
        transactionsRepository.save(rewardTransactions);

        rewardTransactions = RewardTransactions.builder()
                .customer(customer)
                .rewardsEarned(249.98)
                .transStatus("APPROVED")
                .transAmount(199.99)
                .createdDate(getDate(3))
                .build();
        transactionsRepository.save(rewardTransactions);

        rewardTransactions = RewardTransactions.builder()
                .customer(customer)
                .rewardsEarned(49.99)
                .transStatus("APPROVED")
                .transAmount(99.99)
                .createdDate(getDate(3))
                .build();
        transactionsRepository.save(rewardTransactions);

        rewardTransactions = RewardTransactions.builder()
                .customer(customer)
                .rewardsEarned(0.99)
                .transStatus("APPROVED")
                .transAmount(50.99)
                .createdDate(getDate(3))
                .build();
        transactionsRepository.save(rewardTransactions);

        rewardTransactions = RewardTransactions.builder()
                .customer(customer)
                .rewardsEarned(0.99)
                .transStatus("APPROVED")
                .transAmount(50.99)
                .createdDate(getDate(4))
                .build();
        transactionsRepository.save(rewardTransactions);

        //Second Customer
        customer = Customer.builder()
                .fName("Kum")
                .lName("Nag")
                .emailId("Kumnag@gmail.com")
                .build();
        customerRepository.save(customer);

        rewardTransactions = RewardTransactions.builder()
                .customer(customer)
                .rewardsEarned(0.0)
                .transStatus("APPROVED")
                .transAmount(49.01)
                .createdDate(getDate(0))
                .build();
        transactionsRepository.save(rewardTransactions);

        rewardTransactions = RewardTransactions.builder()
                .customer(customer)
                .rewardsEarned(16.99)
                .transStatus("APPROVED")
                .transAmount(66.99)
                .createdDate(getDate(0))
                .build();
        transactionsRepository.save(rewardTransactions);

        rewardTransactions = RewardTransactions.builder()
                .customer(customer)
                .rewardsEarned(36.01)
                .transStatus("APPROVED")
                .transAmount(86.01)
                .createdDate(getDate(1))
                .build();
        transactionsRepository.save(rewardTransactions);

        rewardTransactions = RewardTransactions.builder()
                .customer(customer)
                .rewardsEarned(39.99)
                .transStatus("APPROVED")
                .transAmount(89.99)
                .createdDate(getDate(1))
                .build();
        transactionsRepository.save(rewardTransactions);

        rewardTransactions = RewardTransactions.builder()
                .customer(customer)
                .rewardsEarned(9.99)
                .transStatus("APPROVED")
                .transAmount(59.99)
                .createdDate(getDate(2))
                .build();
        transactionsRepository.save(rewardTransactions);

        rewardTransactions = RewardTransactions.builder()
                .customer(customer)
                .rewardsEarned(249.98)
                .transStatus("APPROVED")
                .transAmount(199.99)
                .createdDate(getDate(3))
                .build();
        transactionsRepository.save(rewardTransactions);

        rewardTransactions = RewardTransactions.builder()
                .customer(customer)
                .rewardsEarned(27.99)
                .transStatus("APPROVED")
                .transAmount(77.99)
                .createdDate(getDate(3))
                .build();
        transactionsRepository.save(rewardTransactions);

        rewardTransactions = RewardTransactions.builder()
                .customer(customer)
                .rewardsEarned(1.99)
                .transStatus("APPROVED")
                .transAmount(51.99)
                .createdDate(getDate(3))
                .build();
        transactionsRepository.save(rewardTransactions);

        rewardTransactions = RewardTransactions.builder()
                .customer(customer)
                .rewardsEarned(0.99)
                .transStatus("APPROVED")
                .transAmount(50.99)
                .createdDate(getDate(4))
                .build();
        transactionsRepository.save(rewardTransactions);
    }

    private Date getDate(int subractMonth) {

        return DateUtils.addMonths(new Date(), -subractMonth);

    }
}
