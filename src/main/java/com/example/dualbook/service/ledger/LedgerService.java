package com.example.dualbook.service.ledger;

import com.example.dualbook.entity.Ledger;
import com.example.dualbook.entity.User;

import java.util.List;

public interface LedgerService {

    // پیدا کردن دفتر بین دو کاربر برای یک ارز خاص
    Ledger findLedgerBetweenUsers(User user1, User user2, String currencySymbol);

    // لیست تمام دفاتر کاربر
    List<Ledger> getUserLedgers(User user);

    // محاسبه مانده کل کاربر
    java.math.BigDecimal getUserTotalBalance(User user);

    // ایجاد دفتر جدید
    Ledger createLedger(User user1, User user2, String currencySymbol);
}