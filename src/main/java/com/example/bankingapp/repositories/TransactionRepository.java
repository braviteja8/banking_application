package com.example.bankingapp.repositories;

import com.example.bankingapp.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    List<Transaction>findByAccountIdOrderByTimeStampDesc(Long id);
}
