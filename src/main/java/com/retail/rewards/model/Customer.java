package com.retail.rewards.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "customer")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cust_id")
    private Long id;

    @Column(name = "first_name")
    @NotBlank(message = "First Name cannot be empty")
    private String fName;

    @Column(name = "last_name")
    @NotBlank(message = "Last Name cannot be empty")
    private String lName;

    @Column(name = "email_id", unique = true)
    @NotBlank(message = "Email Id cannot be empty")
    private String emailId;

    @Column(name = "subscriptions")
    private String subscriptions = "";

    @Transient
    private String action;
}
