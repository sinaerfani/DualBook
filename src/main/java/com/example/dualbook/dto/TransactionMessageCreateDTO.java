package com.example.dualbook.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TransactionMessageCreateDTO {

    @NotNull(message = "Transaction ID is required")
    private Long transactionId;

    @NotBlank(message = "Message content is required")
    private String message;

    // Constructors
    public TransactionMessageCreateDTO() {}

    public TransactionMessageCreateDTO(Long transactionId, String message) {
        this.transactionId = transactionId;
        this.message = message;
    }

    // Getters and Setters
    public Long getTransactionId() { return transactionId; }
    public void setTransactionId(Long transactionId) { this.transactionId = transactionId; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}