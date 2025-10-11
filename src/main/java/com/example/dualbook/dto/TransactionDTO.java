package com.example.dualbook.dto;

import com.example.dualbook.entity.enums.TransactionStatus;
import com.example.dualbook.entity.enums.TransactionType;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionDTO {
    private Long id;
    private Long ledgerId;
    private String senderMobile;
    private String receiverMobile;
    private TransactionType type;
    private BigDecimal amount;
    private TransactionStatus status;
    private String description;
    private LocalDateTime dueDate;
    private LocalDateTime createdAt;
    private LocalDateTime confirmedAt;
    private Long referenceTransactionId;
    private String currencySymbol;

    // Constructors
    public TransactionDTO() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getLedgerId() { return ledgerId; }
    public void setLedgerId(Long ledgerId) { this.ledgerId = ledgerId; }

    public String getSenderMobile() { return senderMobile; }
    public void setSenderMobile(String senderMobile) { this.senderMobile = senderMobile; }

    public String getReceiverMobile() { return receiverMobile; }
    public void setReceiverMobile(String receiverMobile) { this.receiverMobile = receiverMobile; }

    public TransactionType getType() { return type; }
    public void setType(TransactionType type) { this.type = type; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public TransactionStatus getStatus() { return status; }
    public void setStatus(TransactionStatus status) { this.status = status; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getDueDate() { return dueDate; }
    public void setDueDate(LocalDateTime dueDate) { this.dueDate = dueDate; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getConfirmedAt() { return confirmedAt; }
    public void setConfirmedAt(LocalDateTime confirmedAt) { this.confirmedAt = confirmedAt; }

    public Long getReferenceTransactionId() { return referenceTransactionId; }
    public void setReferenceTransactionId(Long referenceTransactionId) { this.referenceTransactionId = referenceTransactionId; }

    public String getCurrencySymbol() { return currencySymbol; }
    public void setCurrencySymbol(String currencySymbol) { this.currencySymbol = currencySymbol; }
}