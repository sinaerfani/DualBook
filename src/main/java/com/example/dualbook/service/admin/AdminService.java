package com.example.dualbook.service.admin;


import com.example.dualbook.entity.Ledger;
import com.example.dualbook.entity.Transaction;
import com.example.dualbook.entity.User;
import com.example.dualbook.entity.enums.RoleName;

import java.util.List;

public interface AdminService {

    // آمار کلی سیستم
    long getUsersCount();
    long getActiveUsersCount();
    long getInactiveUsersCount();
    long getTransactionsCount();
    long getLedgersCount();
    long getPendingTransactionsCount();

    // مدیریت کاربران
    List<User> getAllUsers();
    List<User> getUsersByRole(RoleName role);
    void toggleUserStatus(Long userId);
    void changeUserRole(Long userId, RoleName newRole);

    // مشاهده داده‌ها
    List<Transaction> getAllTransactions();
    List<Ledger> getAllLedgers();

    // جستجو
    List<User> searchUsers(String keyword);
}
