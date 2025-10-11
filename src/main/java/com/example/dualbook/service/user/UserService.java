package com.example.dualbook.service.user;

import com.example.dualbook.entity.User;
import com.example.dualbook.entity.enums.RoleName;
import java.util.List;
import java.util.Optional;

public interface UserService {

    // ثبت کاربر جدید با OTP
    User registerUser(String mobileNumber, String fullName, String otpCode);

    // لاگین با OTP
    User loginWithOtp(String mobileNumber, String otpCode);

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

    // بررسی وجود کاربر با شماره موبایل
    boolean existsByMobileNumber(String mobileNumber);

    // پیدا کردن کاربران بر اساس نقش
    List<User> findByRole(RoleName role);
}