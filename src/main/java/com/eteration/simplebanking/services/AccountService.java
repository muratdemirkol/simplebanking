package com.eteration.simplebanking.services;


import com.eteration.simplebanking.controller.TransactionStatus;
import com.eteration.simplebanking.model.*;
import com.eteration.simplebanking.repository.AccountRepository;
import com.eteration.simplebanking.services.impl.IAccountService;
import com.eteration.simplebanking.services.impl.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

// This class is a place holder you can change the complete implementation
@Service
public class AccountService implements IAccountService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ITransactionService transactionService;

    @Override
    public Account findAccount(String accountNumber) {
        return accountRepository.findById(accountNumber).orElse(null);
    }

    @Override
    public TransactionStatus credit(String accountNumber, double amount) {
        DepositTransaction depositTransaction = new DepositTransaction(amount);
        return post(depositTransaction, accountNumber);
    }

    @Override
    public TransactionStatus debit(String accountNumber, double amount) {
        WithdrawalTransaction withdrawalTransaction = new WithdrawalTransaction(amount);
        return post(withdrawalTransaction, accountNumber);
    }

    @Override
    public TransactionStatus billPayment(String gsmType, String phoneNumber, String accountNumber, double amount) {
        PhoneBillPaymentTransaction billPaymentTransaction = new PhoneBillPaymentTransaction(amount);
        billPaymentTransaction.setTransactionType(gsmType);
        return post(billPaymentTransaction, accountNumber);
    }

    @Override
    public TransactionStatus post(Transaction transaction, String accountNumber) {
        Account account = accountRepository.findById(accountNumber).orElse(null);
        try {
            if (account != null && transaction.doTransactionPost(account)) {
                Transaction persistedTransaction = transactionService.saveTransaction(transaction);
                accountRepository.save(account);
                return new TransactionStatus(HttpStatus.OK.toString(), persistedTransaction.getApprovalCode().toString());
            } else {
                throw new InsufficientBalanceException("Insufficient balance for withdrawal.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            new TransactionStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString(), null);
        }
        return new TransactionStatus(HttpStatus.NOT_FOUND.toString(), null);
    }
}
