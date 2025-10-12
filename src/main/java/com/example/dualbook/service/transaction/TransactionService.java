package com.example.dualbook.service.transaction;

import com.example.dualbook.dto.TransactionCreateDTO;
import com.example.dualbook.dto.TransactionResponseDTO;
import com.example.dualbook.entity.Transaction;
import com.example.dualbook.entity.User;
import com.example.dualbook.entity.enums.TransactionStatus;
import com.example.dualbook.entity.enums.TransactionType;

import java.util.List;

public interface TransactionService {

    // ایجاد تراکنش جدید
    Transaction createTransaction(User sender, TransactionCreateDTO transactionDTO);

    // تایید تراکنش توسط گیرنده
    Transaction confirmTransaction(Long transactionId, User receiver);

    // رد تراکنش توسط گیرنده
    Transaction rejectTransaction(Long transactionId, User receiver, String reason);

    // لغو تراکنش توسط فرستنده
    Transaction cancelTransaction(Long transactionId, User sender);

    // پیدا کردن تراکنش با ID
    Transaction findById(Long id);

    // لیست تراکنش‌های کاربر
    List<Transaction> getUserTransactions(User user);

    // لیست تراکنش‌های در انتظار تایید کاربر
    List<Transaction> getPendingTransactions(User user);

    // ایجاد تراکنش برگشتی
    Transaction createReturnTransaction(User sender, Long originalTransactionId, String description);

    // بررسی وجود تراکنش در انتظار بین دو کاربر
    boolean hasPendingTransactionBetweenUsers(User user1, User user2);
}
