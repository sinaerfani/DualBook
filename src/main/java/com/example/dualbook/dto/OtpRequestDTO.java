package com.example.dualbook.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class OtpRequestDTO {

    @NotBlank(message = "Mobile number is required")
    @Pattern(regexp = "^09[0-9]{9}$", message = "Mobile number must be a valid Iranian number")
    private String mobileNumber;

    // Constructors
    public OtpRequestDTO() {}

    public OtpRequestDTO(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    // Getters and Setters
    public String getMobileNumber() { return mobileNumber; }
    public void setMobileNumber(String mobileNumber) { this.mobileNumber = mobileNumber; }
}