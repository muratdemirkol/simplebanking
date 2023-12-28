package com.eteration.simplebanking.services.impl;

import com.eteration.simplebanking.controller.TransactionStatus;
import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.model.Transaction;

/**
 * Crated By  MuratDemirkol on 26.12.2023
 **/
public interface IAccountService {
    Account findAccount(String accountNumber);

    TransactionStatus credit(String accountNumber, double amount);

    TransactionStatus debit(String accountNumber, double amount);

    TransactionStatus post(Transaction transaction, String accountNumber);

    TransactionStatus billPayment(String gsmType, String phoneNumber, String accountNumber, double amount);
}
