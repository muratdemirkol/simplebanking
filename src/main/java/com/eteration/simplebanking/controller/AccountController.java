package com.eteration.simplebanking.controller;

import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.model.DepositTransaction;
import com.eteration.simplebanking.model.PhoneBillPaymentTransaction;
import com.eteration.simplebanking.model.WithdrawalTransaction;
import com.eteration.simplebanking.services.impl.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// This class is a place holder you can change the complete implementation
@RestController
@RequestMapping("/account/")
public class AccountController {

    @Autowired
    private IAccountService accountService;

    @GetMapping("/{accountNumber}")
    public ResponseEntity<Account> getAccount(@PathVariable String accountNumber) {
        Account account = accountService.findAccount(accountNumber);
        return account != null ? new ResponseEntity<>(account, HttpStatus.OK) : new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/credit/{accountNumber}")
    public ResponseEntity<TransactionStatus> credit(@PathVariable String accountNumber,
                                                    @RequestBody DepositTransaction request) {

        TransactionStatus status = accountService.credit(accountNumber, request.getAmount());
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @PostMapping("/debit/{accountNumber}")
    public ResponseEntity<TransactionStatus> debit(
            @PathVariable String accountNumber,
            @RequestBody WithdrawalTransaction request) {
        TransactionStatus status = accountService.debit(accountNumber, request.getAmount());
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @PostMapping("/billPayment/{gsmType}/{phoneNumber}/{accountNumber}")
    public ResponseEntity<TransactionStatus> billPayment(
            @PathVariable String gsmType,
            @PathVariable String phoneNumber,
            @PathVariable String accountNumber,
            @RequestBody PhoneBillPaymentTransaction request) {
        TransactionStatus status = accountService.billPayment(gsmType, phoneNumber, accountNumber, request.getAmount());
        return new ResponseEntity<>(status, HttpStatus.OK);
    }
}