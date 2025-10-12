package com.example.dualbook.service.message;

import com.example.dualbook.entity.TransactionMessage;
import com.example.dualbook.entity.User;

import java.util.List;

public interface TransactionMessageService {

    // ارسال پیام جدید
    TransactionMessage sendMessage(User sender, Long transactionId, String message);

    // دریافت پیام‌های یک تراکنش
    List<TransactionMessage> getTransactionMessages(Long transactionId);

    // دریافت پیام با ID
    TransactionMessage getMessageById(Long messageId);

    // به‌روزرسانی پیام
    TransactionMessage updateMessage(Long messageId, User user, String newMessage);

    // حذف پیام (حذف منطقی)
    void deleteMessage(Long messageId, User user);
}