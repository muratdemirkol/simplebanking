package com.eteration.simplebanking.model;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDateTime;

// This class is a place holder you can change the complete implementation
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@DiscriminatorValue("WithdrawalTransaction")
public class WithdrawalTransaction extends Transaction {

    public WithdrawalTransaction() {
    }

//    public WithdrawalTransaction(double amount, Account account) {
//        super(amount, account);
//    }

    public WithdrawalTransaction(double amount) {
        super(amount);
    }

    @Override
    public boolean doTransactionPost(Account account) {
        if (account.getBalance() >= getAmount()) {
            account.setBalance(account.getBalance() - getAmount());
            setDate(LocalDateTime.now());
            account.getTransactions().add(this);
            this.setAccount(account);
            return true;
        } else {
            return false;
        }
    }
}


