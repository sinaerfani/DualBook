package com.example.dualbook.mapper;

import com.example.dualbook.dto.TransactionMessageCreateDTO;
import com.example.dualbook.dto.TransactionMessageDTO;
import com.example.dualbook.dto.TransactionMessageResponseDTO;
import com.example.dualbook.entity.TransactionMessage;
import com.example.dualbook.entity.User;
import org.springframework.stereotype.Component;

@Component
public class TransactionMessageMapper {

    public TransactionMessageDTO toDTO(TransactionMessage transactionMessage) {
        if (transactionMessage == null) {
            return null;
        }

        TransactionMessageDTO dto = new TransactionMessageDTO();
        dto.setId(transactionMessage.getId());
        dto.setTransactionId(transactionMessage.getTransaction().getId());
        dto.setSenderName(transactionMessage.getSender().getFullName());
        dto.setSenderMobile(transactionMessage.getSender().getMobileNumber());
        dto.setMessage(transactionMessage.getMessage());
        dto.setCreatedAt(transactionMessage.getCreatedAt());
        dto.setDisableDate(transactionMessage.getDisableDate());

        return dto;
    }

    public TransactionMessageResponseDTO toResponseDTO(TransactionMessage transactionMessage, User currentUser) {
        if (transactionMessage == null) {
            return null;
        }

        TransactionMessageResponseDTO dto = new TransactionMessageResponseDTO();
        dto.setId(transactionMessage.getId());
        dto.setTransactionId(transactionMessage.getTransaction().getId());
        dto.setSenderName(transactionMessage.getSender().getFullName());
        dto.setSenderMobile(transactionMessage.getSender().getMobileNumber());
        dto.setMessage(transactionMessage.getMessage());
        dto.setCreatedAt(transactionMessage.getCreatedAt());

        // بررسی اینکه آیا پیام از کاربر جاری است یا خیر
        if (currentUser != null) {
            dto.setIsFromCurrentUser(transactionMessage.getSender().getId().equals(currentUser.getId()));
        } else {
            dto.setIsFromCurrentUser(false);
        }

        return dto;
    }

    public TransactionMessageResponseDTO toResponseDTO(TransactionMessage transactionMessage) {
        return toResponseDTO(transactionMessage, null);
    }

    // برای تبدیل از DTO به Entity (در صورت نیاز)
    public TransactionMessage toEntity(TransactionMessageCreateDTO createDTO, User sender) {
        if (createDTO == null || sender == null) {
            return null;
        }

        TransactionMessage entity = new TransactionMessage();
        // Note: Transaction باید جداگانه تنظیم شود
        entity.setSender(sender);
        entity.setMessage(createDTO.getMessage());

        return entity;
    }

    // برای به‌روزرسانی Entity از DTO
    public void updateEntityFromDTO(TransactionMessageCreateDTO dto, TransactionMessage entity) {
        if (dto == null || entity == null) {
            return;
        }

        entity.setMessage(dto.getMessage());
    }
}