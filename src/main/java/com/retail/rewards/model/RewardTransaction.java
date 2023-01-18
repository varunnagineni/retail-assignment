package com.retail.rewards.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "reward_transactions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RewardTransaction implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trans_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cust_id")
    private Customer customer;

    @Column(name = "trans_amount")
    private BigDecimal transAmount;

    @Column(name = "trans_status")
    @NotBlank(message = "Transaction status cannot be empty")
    private String transStatus;

    @Column(name = "rewards_earned")
    private BigDecimal rewardsEarned;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "last_modified_date")
    private Date lastModifiedDate;
}
