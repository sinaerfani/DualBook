package com.example.dualbook.repository;

import com.example.dualbook.entity.Transaction;
import com.example.dualbook.entity.TransactionMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionMessageRepository extends JpaRepository<TransactionMessage, Long> {
    // اضافه کردن این متد به ریپازیتوری موجود
    @Query("SELECT tm FROM TransactionMessage tm WHERE tm.transaction.id = :transactionId AND tm.disableDate IS NULL ORDER BY tm.createdAt ASC")
    List<TransactionMessage> findByTransactionIdAndActive(@Param("transactionId") Long transactionId);
    List<TransactionMessage> findByTransactionOrderByCreatedAtAsc(Transaction transaction);

    List<TransactionMessage> findByTransactionAndDisableDateIsNullOrderByCreatedAtAsc(Transaction transaction);
}