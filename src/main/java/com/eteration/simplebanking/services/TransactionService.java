package com.eteration.simplebanking.services;

import com.eteration.simplebanking.model.Transaction;
import com.eteration.simplebanking.repository.TransactionRepository;
import com.eteration.simplebanking.services.impl.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Crated By  MuratDemirkol on 26.12.2023
 **/
@Service
public class TransactionService implements ITransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }
}
