package com.eteration.simplebanking.model;


// This class is a place holder you can change the complete implementation

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "account")
public class Account {
    @Id
    @Column(name = "account_number", unique = true)
    private String accountNumber;

    @Column(name = "owner")
    private String owner;

    @Column(name = "balance")
    private double balance;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Transaction> transactions = new ArrayList<>();


    public Account() {
        this.createDate = LocalDateTime.now();
    }

    public Account(String owner) {
        this.accountNumber = accountNumber;
        this.owner = owner;
        this.balance = 0;
        this.createDate = LocalDateTime.now();
    }

    public Account(String owner, String accountNumber) {
        this.accountNumber = accountNumber;
        this.owner = owner;
        this.balance = 0;
        this.createDate = LocalDateTime.now();
    }

    public void deposit(double amount) {
        balance += amount;
        DepositTransaction depositTransaction = new DepositTransaction(amount);
        post(depositTransaction);
    }

    public void withdraw(double amount) throws InsufficientBalanceException {
        if (amount > balance) {
            throw new InsufficientBalanceException("Insufficient balance for withdrawal.");
        }

        balance -= amount;
        WithdrawalTransaction withdrawalTransaction = new WithdrawalTransaction(amount);
        post(withdrawalTransaction);
    }

    public void post(Transaction transaction) {
        transactions.add(transaction);
    }

}
