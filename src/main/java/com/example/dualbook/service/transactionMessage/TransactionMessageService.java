package com.example.dualbook.service.transactionMessage;

import com.example.dualbook.entity.TransactionMessage;
import com.example.dualbook.entity.User;

import java.util.List;

public interface TransactionMessageService {

    // ارسال پیام جدید
    TransactionMessage sendMessage(User sender, Long transactionId, String message);

    // دریافت پیام‌های یک تراکنش
    List<TransactionMessage> getTransactionMessages(Long transactionId);

    // حذف پیام (حذف منطقی)
    void deleteMessage(Long messageId, User user);
}