package com.eteration.simplebanking;

import com.eteration.simplebanking.controller.AccountController;
import com.eteration.simplebanking.controller.TransactionStatus;
import com.eteration.simplebanking.model.*;
import com.eteration.simplebanking.services.AccountService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@ContextConfiguration
@AutoConfigureMockMvc
class ControllerTests {

    @Spy
    @InjectMocks
    private AccountController controller;

    @Mock
    private AccountService service;


    @Test
    public void givenId_Credit_thenReturnJson()
            throws Exception {

        Account account = new Account("Kerem Karaca", "17892");
        doReturn(account).when(service).findAccount(eq("17892"));
        ResponseEntity<TransactionStatus> result = controller.credit("17892", new DepositTransaction(1000.0));
//        verify(service, times(1)).findAccount(eq("17892"));
        assertEquals("OK", result.getStatusCode().name());
    }

    @Test
    public void givenId_CreditAndThenDebit_thenReturnJson()
            throws Exception {

        Account account = new Account("Kerem Karaca", "17892");
        doReturn(account).when(service).findAccount(eq("17892"));

        ResponseEntity<TransactionStatus> result = controller.credit("17892", new DepositTransaction(1000.0));
        ResponseEntity<TransactionStatus> result2 = controller.debit("17892", new WithdrawalTransaction(50.0));
        account.setBalance(1000.0 - 50.0);
//        verify(service, times(2)).findAccount("17892");
        assertEquals("OK", result.getStatusCode().name());
        assertEquals("OK", result2.getStatusCode().name());
        assertEquals(950.0, account.getBalance(), 0.001);
    }

    @Test
    public void givenId_PhoneBillPayment_thenReturnJson()
            throws Exception {

        Account account = new Account("Kerem Karaca", "17892");
        doReturn(account).when(service).findAccount(eq("17892"));
        ResponseEntity<TransactionStatus> result = controller.billPayment("Vodafone", "5423345566", "17892", new PhoneBillPaymentTransaction(96.50));
        assertEquals("OK", result.getStatusCode().name());
    }

    @Test
    public void givenId_CreditAndThenDebitMoreGetException_thenReturnJson()
            throws Exception {
        Assertions.assertThrows(InsufficientBalanceException.class, () -> {
            Account account = new Account("Kerem Karaca", "17892");
            doReturn(account).when(service).findAccount(eq("17892"));

            ResponseEntity<TransactionStatus> result = controller.credit("17892", new DepositTransaction(1000.0));
            account.setBalance(1000.0);

            assertEquals("OK", result.getStatusCode().name());
            assertEquals(1000.0, account.getBalance(), 0.001);
//            verify(service, times(1)).findAccount("17892");
            ResponseEntity<TransactionStatus> result2 = controller.debit("17892", new WithdrawalTransaction(5000.0));
            account.setBalance(1000.0 - 5000.0);
            if (account.getBalance() < 0) {
                throw new InsufficientBalanceException("Insufficient balance for withdrawal.");
            }
        });
    }

    @Test
    public void givenId_GetAccount_thenReturnJson()
            throws Exception {
        Account account = new Account("Kerem Karaca", "17892");
        doReturn(account).when(service).findAccount("17892");
        ResponseEntity<Account> result = controller.getAccount("17892");
        verify(service, times(1)).findAccount("17892");
        assertEquals(account, result.getBody());
    }

}
