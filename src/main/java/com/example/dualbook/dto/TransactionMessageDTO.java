package com.example.dualbook.dto;

import java.time.LocalDateTime;

public class TransactionMessageDTO {
    private Long id;
    private Long transactionId;
    private String senderName;
    private String message;
    private LocalDateTime createdAt;

    // Constructors
    public TransactionMessageDTO() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getTransactionId() { return transactionId; }
    public void setTransactionId(Long transactionId) { this.transactionId = transactionId; }

    public String getSenderName() { return senderName; }
    public void setSenderName(String senderName) { this.senderName = senderName; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}