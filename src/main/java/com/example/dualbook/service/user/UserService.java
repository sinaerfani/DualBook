package com.example.dualbook.service.user;

import com.example.dualbook.entity.User;
import com.example.dualbook.entity.enums.RoleName;

import java.util.List;
import java.util.Optional;

public interface UserService {

    // ثبت کاربر جدید
    User registerUser(String mobileNumber, String password, String fullName);

    // پیدا کردن کاربر با شماره موبایل
    Optional<User> findByMobileNumber(String mobileNumber);

    // پیدا کردن کاربر با ID
    Optional<User> findById(Long id);

    // لیست تمام کاربران فعال
    List<User> findAllActiveUsers();

    // لیست تمام کاربران غیرفعال
    List<User> findAllInactiveUsers();

    // غیرفعال کردن کاربر
    void disableUser(Long userId);

    // فعال کردن کاربر
    void enableUser(Long userId);

    // تغییر نقش کاربر
    void changeUserRole(Long userId, RoleName newRole);

    // به‌روزرسانی اطلاعات کاربر
    User updateUser(Long userId, String fullName);

    // تغییر رمز عبور
    void changePassword(Long userId, String newPassword);

    // بررسی وجود کاربر با شماره موبایل
    boolean existsByMobileNumber(String mobileNumber);

}