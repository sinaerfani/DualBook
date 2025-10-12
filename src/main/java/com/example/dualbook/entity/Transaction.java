package com.example.dualbook.entity;

import com.example.dualbook.entity.enums.TransactionStatus;
import com.example.dualbook.entity.enums.TransactionType;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction extends BaseEntity{



    private Ledger ledger;
    private User sender;
    private User receiver;
    private TransactionType type;
    private BigDecimal amount;
    private TransactionStatus status = TransactionStatus.PENDING;
    private String description;
    private LocalDateTime dueDate;
    private LocalDateTime confirmedAt;
    private Transaction referenceTransaction; // برای تراکنش‌های برگشتی



    // Getters and Setters
    @ManyToOne
    @JoinColumn(name = "ledger_id", nullable = false)
    public Ledger getLedger() { return ledger; }
    public void setLedger(Ledger ledger) { this.ledger = ledger; }
    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    public User getSender() { return sender; }
    public void setSender(User sender) { this.sender = sender; }

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    public User getReceiver() { return receiver; }
    public void setReceiver(User receiver) { this.receiver = receiver; }
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public TransactionType getType() { return type; }
    public void setType(TransactionType type) { this.type = type; }
    @Column(precision = 15, scale = 6, nullable = false)
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public TransactionStatus getStatus() { return status; }
    public void setStatus(TransactionStatus status) { this.status = status; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getDueDate() { return dueDate; }
    public void setDueDate(LocalDateTime dueDate) { this.dueDate = dueDate; }


    public LocalDateTime getConfirmedAt() { return confirmedAt; }
    public void setConfirmedAt(LocalDateTime confirmedAt) { this.confirmedAt = confirmedAt; }
    @ManyToOne
    @JoinColumn(name = "reference_transaction_id")
    public Transaction getReferenceTransaction() { return referenceTransaction; }
    public void setReferenceTransaction(Transaction referenceTransaction) { this.referenceTransaction = referenceTransaction; }
}


