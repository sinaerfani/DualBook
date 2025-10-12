package com.example.dualbook.service.otp;

import com.example.dualbook.entity.OtpCode;
import com.example.dualbook.entity.User;
import com.example.dualbook.repository.OtpCodeRepository;
import com.example.dualbook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@Transactional
public class OtpServiceImpl implements OtpService {

    private final OtpCodeRepository otpCodeRepository;
    private final UserRepository userRepository;

    @Value("${app.otp.expiry-minutes:5}")
    private int otpExpiryMinutes;

    @Value("${app.otp.max-attempts:3}")
    private int maxAttempts;

    @Value("${app.otp.daily-limit:3}")
    private int dailyLimit;

    public OtpServiceImpl(OtpCodeRepository otpCodeRepository, UserRepository userRepository) {
        this.otpCodeRepository = otpCodeRepository;
        this.userRepository = userRepository;
    }

    @Override
    public boolean generateAndSendOtp(String mobileNumber) {
        if (!canSendOtp(mobileNumber)) {
            return false;
        }

        // غیرفعال کردن OTPهای قبلی
        deactivatePreviousOtps(mobileNumber);

        // تولید کد OTP
        String otpCode = generateOtpCode();

        // ذخیره OTP در دیتابیس
        OtpCode otp = new OtpCode();
        otp.setMobileNumber(mobileNumber);
        otp.setCode(otpCode);
        otp.setExpiresAt(LocalDateTime.now().plusMinutes(otpExpiryMinutes));
        otpCodeRepository.save(otp);

        // به‌روزرسانی شمارنده درخواست‌های کاربر
        updateUserOtpRequestCount(mobileNumber);

        // در اینجا کد ارسال پیامک را قرار می‌دهیم
        // sendSms(mobileNumber, otpCode);

        // برای تست، کد OTP را در کنسول چاپ می‌کنیم
        System.out.println("OTP for " + mobileNumber + ": " + otpCode);

        return true;
    }

    @Override
    public boolean verifyOtp(String mobileNumber, String code) {
        Optional<OtpCode> otpOptional = otpCodeRepository.findByMobileNumberAndCode(mobileNumber, code);

        if (otpOptional.isEmpty()) {
            return false;
        }

        OtpCode otp = otpOptional.get();

        // بررسی انقضا
        if (otp.isExpired()) {
            return false;
        }

        // بررسی استفاده شده
        if (otp.getUsed()) {
            return false;
        }

        // بررسی تعداد تلاش‌ها
        if (otp.getAttemptCount() >= maxAttempts) {
            return false;
        }

        // افزایش شمارنده تلاش
        otp.incrementAttempt();

        // اگر کد صحیح است، آن را استفاده شده علامت بزن
        if (otp.getCode().equals(code)) {
            otp.setUsed(true);
            otpCodeRepository.save(otp);
            return true;
        }

        otpCodeRepository.save(otp);
        return false;
    }

    @Override
    public boolean canSendOtp(String mobileNumber) {
        // بررسی کاربر موجود
        Optional<User> userOptional = userRepository.findByMobileNumber(mobileNumber);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // بررسی reset روزانه
            if (user.getLastOtpResetDate() == null ||
                    user.getLastOtpResetDate().toLocalDate().isBefore(LocalDateTime.now().toLocalDate())) {
                user.setOtpRequestsToday(0);
                user.setLastOtpResetDate(LocalDateTime.now());
                userRepository.save(user);
            }

            // بررسی محدودیت روزانه
            if (user.getOtpRequestsToday() >= dailyLimit) {
                return false;
            }
        }

        // بررسی کلی محدودیت در ۲۴ ساعت گذشته
        LocalDateTime twentyFourHoursAgo = LocalDateTime.now().minusHours(24);
        long requestsIn24Hours = otpCodeRepository.countOtpRequestsSince(mobileNumber, twentyFourHoursAgo);

        return requestsIn24Hours < dailyLimit;
    }

    @Override
    @Scheduled(fixedRate = 300000) // هر ۵ دقیقه
    public void cleanupExpiredOtps() {
        List<OtpCode> expiredOtps = otpCodeRepository.findAllExpiredOtpCodes(LocalDateTime.now());
        otpCodeRepository.deleteAll(expiredOtps);
    }

    private void deactivatePreviousOtps(String mobileNumber) {
        List<OtpCode> validOtps = otpCodeRepository.findValidOtpByMobileNumber(mobileNumber, LocalDateTime.now());
        for (OtpCode otp : validOtps) {
            otp.setUsed(true);
        }
        otpCodeRepository.saveAll(validOtps);
    }

    private void updateUserOtpRequestCount(String mobileNumber) {
        Optional<User> userOptional = userRepository.findByMobileNumber(mobileNumber);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setOtpRequestsToday(user.getOtpRequestsToday() + 1);
            user.setLastOtpRequest(LocalDateTime.now());
            userRepository.save(user);
        } else {
            // اگر کاربر وجود ندارد، یک کاربر موقت ایجاد می‌کنیم
            User tempUser = new User();
            tempUser.setMobileNumber(mobileNumber);
            tempUser.setFullName("Temp User");
            tempUser.setOtpRequestsToday(1);
            tempUser.setLastOtpRequest(LocalDateTime.now());
            tempUser.setLastOtpResetDate(LocalDateTime.now());
            userRepository.save(tempUser);
        }
    }

    private String generateOtpCode() {
        Random random = new Random();
        int code = 1000 + random.nextInt(9000); // کد ۴ رقمی
        return String.valueOf(code);
    }
}