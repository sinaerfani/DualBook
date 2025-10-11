package com.example.dualbook.dto;

import com.example.dualbook.entity.enums.RoleName;
import java.time.LocalDateTime;

public class UserDTO {
    private Long id;
    private String mobileNumber;
    private String fullName;
    private RoleName role;
    private LocalDateTime createdAt;
    private LocalDateTime disableDate;

    // Constructors
    public UserDTO() {}

    public UserDTO(Long id, String mobileNumber, String fullName, RoleName role,
                   LocalDateTime createdAt, LocalDateTime disableDate) {
        this.id = id;
        this.mobileNumber = mobileNumber;
        this.fullName = fullName;
        this.role = role;
        this.createdAt = createdAt;
        this.disableDate = disableDate;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMobileNumber() { return mobileNumber; }
    public void setMobileNumber(String mobileNumber) { this.mobileNumber = mobileNumber; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public RoleName getRole() { return role; }
    public void setRole(RoleName role) { this.role = role; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getDisableDate() { return disableDate; }
    public void setDisableDate(LocalDateTime disableDate) { this.disableDate = disableDate; }
}