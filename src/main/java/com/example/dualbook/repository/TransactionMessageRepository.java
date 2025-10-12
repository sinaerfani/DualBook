package com.example.dualbook.repository;

import com.example.dualbook.entity.Transaction;
import com.example.dualbook.entity.TransactionMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionMessageRepository extends JpaRepository<TransactionMessage, Long> {

    List<TransactionMessage> findByTransactionOrderByCreatedAtAsc(Transaction transaction);

    List<TransactionMessage> findByTransactionAndDisableDateIsNullOrderByCreatedAtAsc(Transaction transaction);
}