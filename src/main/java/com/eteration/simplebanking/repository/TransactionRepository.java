package com.eteration.simplebanking.repository;

import com.eteration.simplebanking.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Crated By  MuratDemirkol on 26.12.2023
 **/
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
