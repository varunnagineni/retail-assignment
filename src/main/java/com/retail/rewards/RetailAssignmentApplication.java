package com.retail.rewards;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.retail.rewards")
@EnableJpaRepositories(basePackages = "com.retail.rewards.repository")
@EntityScan("com.retail.rewards.model")
public class RetailAssignmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(RetailAssignmentApplication.class, args);
    }

}
