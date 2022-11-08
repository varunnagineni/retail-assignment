package com.retail.rewards.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "customer")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cust_id", nullable = false)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String fName;

    @Column(name = "last_name", nullable = false)
    private String lName;

    @Column(name = "email_id", unique = true, nullable = false)
    private String emailId;

    @Column(name = "subscriptions")
    private String subscriptions = "";

    @Transient
    private String action;
}
