package com.example.dualbook.repository;

import com.example.dualbook.entity.Transaction;
import com.example.dualbook.entity.enums.TransactionStatus;
import com.example.dualbook.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT t FROM Transaction t WHERE t.sender = :sender AND t.status = :status AND t.disableDate IS NULL")
    List<Transaction> findBySenderAndStatusAndActive(@Param("sender") User sender, @Param("status") TransactionStatus status);

    @Query("SELECT t FROM Transaction t WHERE t.receiver = :receiver AND t.status = :status AND t.disableDate IS NULL")
    List<Transaction> findByReceiverAndStatusAndActive(@Param("receiver") User receiver, @Param("status") TransactionStatus status);

    @Query("SELECT t FROM Transaction t WHERE (t.sender = :user OR t.receiver = :user) AND t.status = :status AND t.disableDate IS NULL")
    List<Transaction> findByUserAndStatusAndActive(@Param("user") User user, @Param("status") TransactionStatus status);

    @Query("SELECT t FROM Transaction t WHERE t.ledger.id = :ledgerId AND t.disableDate IS NULL ORDER BY t.createdAt DESC")
    List<Transaction> findByLedgerIdAndActiveOrderByCreatedAtDesc(@Param("ledgerId") Long ledgerId);

    @Query("SELECT COUNT(t) FROM Transaction t WHERE ((t.sender = :user1 AND t.receiver = :user2) OR (t.sender = :user2 AND t.receiver = :user1)) AND t.status = 'PENDING' AND t.disableDate IS NULL")
    long countPendingActiveTransactionsBetweenUsers(@Param("user1") User user1, @Param("user2") User user2);

    @Query("SELECT t FROM Transaction t WHERE t.disableDate IS NULL")
    List<Transaction> findAllActive();
}