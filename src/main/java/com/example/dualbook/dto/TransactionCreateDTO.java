package com.example.dualbook.dto;

import com.example.dualbook.entity.enums.TransactionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionCreateDTO {

    @NotBlank(message = "Receiver mobile number is required")
    private String receiverMobile;

    @NotNull(message = "Transaction type is required")
    private TransactionType type;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;

    @NotBlank(message = "Currency symbol is required")
    private String currencySymbol;

    private String description;
    private LocalDateTime dueDate;

    // Constructors
    public TransactionCreateDTO() {}

    // Getters and Setters
    public String getReceiverMobile() { return receiverMobile; }
    public void setReceiverMobile(String receiverMobile) { this.receiverMobile = receiverMobile; }

    public TransactionType getType() { return type; }
    public void setType(TransactionType type) { this.type = type; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getCurrencySymbol() { return currencySymbol; }
    public void setCurrencySymbol(String currencySymbol) { this.currencySymbol = currencySymbol; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getDueDate() { return dueDate; }
    public void setDueDate(LocalDateTime dueDate) { this.dueDate = dueDate; }
}