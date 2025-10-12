package com.example.dualbook.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "otp_codes")
public class OtpCode extends BaseEntity {


    private String mobileNumber;

    private String code;

    private LocalDateTime expiresAt;

    private Boolean used = false;

    private Integer attemptCount = 0;

    // Constructors
    public OtpCode() {}

    public OtpCode(String mobileNumber, String code, LocalDateTime expiresAt) {
        this.mobileNumber = mobileNumber;
        this.code = code;
        this.expiresAt = expiresAt;
        this.used = false;
        this.attemptCount = 0;
    }

    // Getters and Setters
    @Column(nullable = false)
    public String getMobileNumber() { return mobileNumber; }
    public void setMobileNumber(String mobileNumber) { this.mobileNumber = mobileNumber; }
    @Column(nullable = false)
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    @Column(nullable = false)
    public LocalDateTime getExpiresAt() { return expiresAt; }
    public void setExpiresAt(LocalDateTime expiresAt) { this.expiresAt = expiresAt; }
    @Column(nullable = false)
    public Boolean getUsed() { return used; }
    public void setUsed(Boolean used) { this.used = used; }
    @Column(nullable = false)
    public Integer getAttemptCount() { return attemptCount; }
    public void setAttemptCount(Integer attemptCount) { this.attemptCount = attemptCount; }

    // Utility methods
    @Transient
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt);
    }
    @Transient
    public boolean isValid() {
        return !used && !isExpired();
    }
    @Transient
    public void incrementAttempt() {
        this.attemptCount++;
    }
}