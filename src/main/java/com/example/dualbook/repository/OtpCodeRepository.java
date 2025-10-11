package com.example.dualbook.repository;

import com.example.dualbook.entity.OtpCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OtpCodeRepository extends JpaRepository<OtpCode, Long> {

    // پیدا کردن آخرین OTP معتبر برای یک شماره موبایل
    @Query("SELECT o FROM OtpCode o WHERE o.mobileNumber = :mobileNumber AND o.used = false AND o.expiresAt > :now ORDER BY o.createdAt DESC")
    List<OtpCode> findValidOtpByMobileNumber(@Param("mobileNumber") String mobileNumber, @Param("now") LocalDateTime now);

    // شمارش OTPهای ارسال شده در ۲۴ ساعت گذشته برای یک شماره
    @Query("SELECT COUNT(o) FROM OtpCode o WHERE o.mobileNumber = :mobileNumber AND o.createdAt > :since")
    long countOtpRequestsSince(@Param("mobileNumber") String mobileNumber, @Param("since") LocalDateTime since);

    // پیدا کردن OTP با کد و شماره موبایل
    Optional<OtpCode> findByMobileNumberAndCode(String mobileNumber, String code);

    // پیدا کردن تمام OTPهای منقضی شده
    @Query("SELECT o FROM OtpCode o WHERE o.expiresAt <= :now AND o.used = false")
    List<OtpCode> findAllExpiredOtpCodes(@Param("now") LocalDateTime now);
}
