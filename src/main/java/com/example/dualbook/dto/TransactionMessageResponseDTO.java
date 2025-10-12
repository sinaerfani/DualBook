package com.example.dualbook.dto;

import java.time.LocalDateTime;

public class TransactionMessageResponseDTO {
    private Long id;
    private Long transactionId;
    private String senderName;
    private String senderMobile;
    private String message;
    private LocalDateTime createdAt;
    private Boolean isFromCurrentUser;

    // Constructors
    public TransactionMessageResponseDTO() {}

    public TransactionMessageResponseDTO(Long id, Long transactionId, String senderName, String senderMobile,
                                         String message, LocalDateTime createdAt, Boolean isFromCurrentUser) {
        this.id = id;
        this.transactionId = transactionId;
        this.senderName = senderName;
        this.senderMobile = senderMobile;
        this.message = message;
        this.createdAt = createdAt;
        this.isFromCurrentUser = isFromCurrentUser;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getTransactionId() { return transactionId; }
    public void setTransactionId(Long transactionId) { this.transactionId = transactionId; }

    public String getSenderName() { return senderName; }
    public void setSenderName(String senderName) { this.senderName = senderName; }

    public String getSenderMobile() { return senderMobile; }
    public void setSenderMobile(String senderMobile) { this.senderMobile = senderMobile; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public Boolean getIsFromCurrentUser() { return isFromCurrentUser; }
    public void setIsFromCurrentUser(Boolean fromCurrentUser) { isFromCurrentUser = fromCurrentUser; }
}