package com.eteration.simplebanking.model;


// This class is a place holder you can change the complete implementation

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@DiscriminatorValue("DepositTransaction")
public class DepositTransaction extends Transaction {

    public DepositTransaction() {
    }

    public DepositTransaction(double amount) {
        super(amount);
    }

    @Override
    public boolean doTransactionPost(Account account) {
        account.setBalance(account.getBalance() + getAmount());
        setDate(LocalDateTime.now());
        account.getTransactions().add(this);
        this.setAccount(account);
        return true;
    }
}
