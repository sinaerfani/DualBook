package com.example.dualbook.mapper;

import com.example.dualbook.dto.TransactionDTO;
import com.example.dualbook.dto.TransactionResponseDTO;
import com.example.dualbook.entity.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    public TransactionDTO toDTO(Transaction transaction) {
        if (transaction == null) {
            return null;
        }

        TransactionDTO dto = new TransactionDTO();
        dto.setId(transaction.getId());
        dto.setLedgerId(transaction.getLedger().getId());
        dto.setSenderMobile(transaction.getSender().getMobileNumber());
        dto.setReceiverMobile(transaction.getReceiver().getMobileNumber());
        dto.setType(transaction.getType());
        dto.setAmount(transaction.getAmount());
        dto.setStatus(transaction.getStatus());
        dto.setDescription(transaction.getDescription());
        dto.setDueDate(transaction.getDueDate());
        dto.setCreatedAt(transaction.getCreatedAt());
        dto.setConfirmedAt(transaction.getConfirmedAt());
        dto.setCurrencySymbol(transaction.getLedger().getCurrencyType().getSymbol());

        if (transaction.getReferenceTransaction() != null) {
            dto.setReferenceTransactionId(transaction.getReferenceTransaction().getId());
        }

        return dto;
    }

    public TransactionResponseDTO toResponseDTO(Transaction transaction) {
        if (transaction == null) {
            return null;
        }

        TransactionResponseDTO dto = new TransactionResponseDTO();
        dto.setId(transaction.getId());
        dto.setSenderName(transaction.getSender().getFullName());
        dto.setReceiverName(transaction.getReceiver().getFullName());
        dto.setType(transaction.getType());
        dto.setAmount(transaction.getAmount());
        dto.setStatus(transaction.getStatus());
        dto.setDescription(transaction.getDescription());
        dto.setDueDate(transaction.getDueDate());
        dto.setCreatedAt(transaction.getCreatedAt());
        dto.setCurrencySymbol(transaction.getLedger().getCurrencyType().getSymbol());

        return dto;
    }
}