package com.eteration.simplebanking.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

// This class is a place holder you can change the complete implementation
@Data
@Entity
@Table(name = "transactions")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "transaction_type", discriminatorType = DiscriminatorType.STRING)
public abstract class Transaction implements CanDoTransactionPost {
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(generator = "uuid2")
    @Column(name = "approval_code", columnDefinition = "VARCHAR(255)")
    private UUID approvalCode;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "amount")
    private double amount;

    @Column(name = "transaction_type", insertable = false, updatable = false)
    private String transactionType;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "account_number")
    private Account account;

    public Transaction() {
        this.date = LocalDateTime.now();
    }

    public Transaction(double amount) {
        this.date = LocalDateTime.now();
        this.amount = amount;
    }

    public Transaction(double amount, String transactionType, String phoneNumber) {
        this.account = new Account(phoneNumber);
        this.amount = amount;
        this.transactionType = transactionType;
    }
}
