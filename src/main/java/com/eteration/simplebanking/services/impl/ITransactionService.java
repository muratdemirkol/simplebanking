package com.eteration.simplebanking.services.impl;

import com.eteration.simplebanking.model.Transaction;

/**
 * Crated By  MuratDemirkol on 26.12.2023
 **/

public interface ITransactionService {
    Transaction saveTransaction(Transaction transaction);

    ;
}
