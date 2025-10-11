package com.example.dualbook.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserRegistrationDTO {

    @NotBlank(message = "Mobile number is required")
    @Pattern(regexp = "^09[0-9]{9}$", message = "Mobile number must be a valid Iranian number")
    private String mobileNumber;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @NotBlank(message = "Full name is required")
    @Size(min = 2, max = 100, message = "Full name must be between 2 and 100 characters")
    private String fullName;

    // Constructors
    public UserRegistrationDTO() {}

    public UserRegistrationDTO(String mobileNumber, String password, String fullName) {
        this.mobileNumber = mobileNumber;
        this.password = password;
        this.fullName = fullName;
    }

    // Getters and Setters
    public String getMobileNumber() { return mobileNumber; }
    public void setMobileNumber(String mobileNumber) { this.mobileNumber = mobileNumber; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
}