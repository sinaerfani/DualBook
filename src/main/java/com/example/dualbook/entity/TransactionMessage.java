package com.example.dualbook.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaction_messages")
public class TransactionMessage extends BaseEntity{


    private Transaction transaction;


    private User sender;


    private String message;




    // Getters and Setters
    @ManyToOne
    @JoinColumn(name = "transaction_id", nullable = false)
    public Transaction getTransaction() { return transaction; }
    public void setTransaction(Transaction transaction) { this.transaction = transaction; }
    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    public User getSender() { return sender; }
    public void setSender(User sender) { this.sender = sender; }
    @Column(nullable = false, columnDefinition = "TEXT")
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

}