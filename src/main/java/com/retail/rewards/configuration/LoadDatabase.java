package com.retail.rewards.configuration;

import com.retail.rewards.model.Customer;
import com.retail.rewards.model.RewardTransactions;
import com.retail.rewards.repository.CustomerRepository;
import com.retail.rewards.repository.TransactionsRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

        Customer customer = Customer.builder()
                .fName("Var")
                .lName("Nag")
                .emailId("varna@gmail.com")
                .build();
        customerRepository.save(customer);
        log.info("Customer : {} ", customer);

        RewardTransactions rewardTransactions = RewardTransactions.builder()
                .customer(customer)
                .rewardsEarned(100)
                .transStatus("SUCCESS")
                .transAmount(150.51)
                .createdDate(new Date())
                .build();
        transactionsRepository.save(rewardTransactions);
        log.info("RewardTransaction : {} ", rewardTransactions);

        rewardTransactions = RewardTransactions.builder()
                .customer(customer)
                .rewardsEarned(102)
                .transStatus("SUCCESS")
                .transAmount(151.51)
                .createdDate(new Date())
                .build();
        transactionsRepository.save(rewardTransactions);
        log.info("RewardTransaction : {} ", rewardTransactions);

        rewardTransactions = RewardTransactions.builder()
                .customer(customer)
                .rewardsEarned(152)
                .transStatus("SUCCESS")
                .transAmount(176.51)
                .createdDate(new Date())
                .build();
        transactionsRepository.save(rewardTransactions);
        log.info("RewardTransaction : {} ", rewardTransactions);

        rewardTransactions = RewardTransactions.builder()
                .customer(customer)
                .rewardsEarned(150)
                .transStatus("SUCCESS")
                .transAmount(175.51)
                .createdDate(new Date())
                .build();
        transactionsRepository.save(rewardTransactions);
        log.info("RewardTransaction : {} ", rewardTransactions);

        rewardTransactions = RewardTransactions.builder()
                .customer(customer)
                .rewardsEarned(200)
                .transStatus("SUCCESS")
                .transAmount(200)
                .createdDate(new Date())
                .build();
        transactionsRepository.save(rewardTransactions);
        log.info("RewardTransaction : {} ", rewardTransactions);

        rewardTransactions = RewardTransactions.builder()
                .customer(customer)
                .rewardsEarned(150)
                .transStatus("SUCCESS")
                .transAmount(176.51)
                .createdDate(new Date())
                .build();
        transactionsRepository.save(rewardTransactions);
        log.info("RewardTransaction : {} ", rewardTransactions);

        rewardTransactions = RewardTransactions.builder()
                .customer(customer)
                .rewardsEarned(150)
                .transStatus("SUCCESS")
                .transAmount(176.51)
                .createdDate(new Date())
                .build();
        transactionsRepository.save(rewardTransactions);
        log.info("RewardTransaction : {} ", rewardTransactions);

        rewardTransactions = RewardTransactions.builder()
                .customer(customer)
                .rewardsEarned(150)
                .transStatus("SUCCESS")
                .transAmount(176.51)
                .createdDate(new Date())
                .build();
        transactionsRepository.save(rewardTransactions);
        log.info("RewardTransaction : {} ", rewardTransactions);

        customer = Customer.builder()
                .fName("Va")
                .lName("Na")
                .emailId("varnag@gmail.com")
                .build();
        customerRepository.save(customer);
        log.info("Customer : {} ", customer);

        rewardTransactions = RewardTransactions.builder()
                .customer(customer)
                .rewardsEarned(100)
                .transStatus("SUCCESS")
                .transAmount(176.51)
                .createdDate(new Date())
                .build();
        transactionsRepository.save(rewardTransactions);
        log.info("RewardTransaction : {} ", rewardTransactions);

        rewardTransactions = RewardTransactions.builder()
                .customer(customer)
                .rewardsEarned(125)
                .transStatus("SUCCESS")
                .transAmount(176.51)
                .createdDate(new Date())
                .build();
        transactionsRepository.save(rewardTransactions);
        log.info("RewardTransaction : {} ", rewardTransactions);
    }

    private Date getDate(int subractMonth) throws ParseException {

        Calendar now = Calendar.getInstance();

        now.add(Calendar.MONTH, -subractMonth);

        Date utilDate = now.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.parse(String.valueOf(utilDate));

    }
}
