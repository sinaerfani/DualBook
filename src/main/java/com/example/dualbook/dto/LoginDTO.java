package com.example.dualbook.dto;

import jakarta.validation.constraints.NotBlank;

public class LoginDTO {

    @NotBlank(message = "Mobile number is required")
    private String mobileNumber;

    @NotBlank(message = "Password is required")
    private String password;

    // Constructors
    public LoginDTO() {}

    public LoginDTO(String mobileNumber, String password) {
        this.mobileNumber = mobileNumber;
        this.password = password;
    }

    // Getters and Setters
    public String getMobileNumber() { return mobileNumber; }
    public void setMobileNumber(String mobileNumber) { this.mobileNumber = mobileNumber; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}