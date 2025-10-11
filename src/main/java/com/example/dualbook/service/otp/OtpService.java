package com.example.dualbook.service.otp;

import com.example.dualbook.entity.OtpCode;

public interface OtpService {

    // تولید و ارسال OTP
    boolean generateAndSendOtp(String mobileNumber);

    // بررسی اعتبار OTP
    boolean verifyOtp(String mobileNumber, String code);

    // بررسی محدودیت ارسال OTP
    boolean canSendOtp(String mobileNumber);

    // پاکسازی OTPهای منقضی شده
    void cleanupExpiredOtps();
}