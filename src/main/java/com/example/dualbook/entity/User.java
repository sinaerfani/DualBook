package com.example.dualbook.entity;

import com.example.dualbook.entity.enums.RoleName;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @NotBlank
    @Column(unique = true, nullable = false)
    private String mobileNumber;

    private String fullName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleName role = RoleName.ROLE_USER;

    @Column(name = "last_otp_request")
    private LocalDateTime lastOtpRequest;

    @Column(name = "otp_requests_today")
    private Integer otpRequestsToday = 0;

    @Column(name = "last_otp_reset_date")
    private LocalDateTime lastOtpResetDate;

    @OneToMany(mappedBy = "user1", cascade = CascadeType.ALL)
    private List<Ledger> ledgersAsUser1 = new ArrayList<>();

    @OneToMany(mappedBy = "user2", cascade = CascadeType.ALL)
    private List<Ledger> ledgersAsUser2 = new ArrayList<>();

    // Constructors
    public User() {}

    public User(String mobileNumber, String fullName, RoleName role) {
        this.mobileNumber = mobileNumber;
        this.fullName = fullName;
        this.role = role;
        this.otpRequestsToday = 0;
        this.lastOtpResetDate = LocalDateTime.now();
    }

    // Getters and Setters
    public String getMobileNumber() { return mobileNumber; }
    public void setMobileNumber(String mobileNumber) { this.mobileNumber = mobileNumber; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public RoleName getRole() { return role; }
    public void setRole(RoleName role) { this.role = role; }

    public LocalDateTime getLastOtpRequest() { return lastOtpRequest; }
    public void setLastOtpRequest(LocalDateTime lastOtpRequest) { this.lastOtpRequest = lastOtpRequest; }

    public Integer getOtpRequestsToday() { return otpRequestsToday; }
    public void setOtpRequestsToday(Integer otpRequestsToday) { this.otpRequestsToday = otpRequestsToday; }

    public LocalDateTime getLastOtpResetDate() { return lastOtpResetDate; }
    public void setLastOtpResetDate(LocalDateTime lastOtpResetDate) { this.lastOtpResetDate = lastOtpResetDate; }

    public List<Ledger> getLedgersAsUser1() { return ledgersAsUser1; }
    public void setLedgersAsUser1(List<Ledger> ledgersAsUser1) { this.ledgersAsUser1 = ledgersAsUser1; }

    public List<Ledger> getLedgersAsUser2() { return ledgersAsUser2; }
    public void setLedgersAsUser2(List<Ledger> ledgersAsUser2) { this.ledgersAsUser2 = ledgersAsUser2; }
}