package com.example.dualbook.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class OtpVerifyDTO {

    @NotBlank(message = "Mobile number is required")
    @Pattern(regexp = "^09[0-9]{9}$", message = "Mobile number must be a valid Iranian number")
    private String mobileNumber;

    @NotBlank(message = "OTP code is required")
    @Size(min = 4, max = 6, message = "OTP code must be between 4 and 6 characters")
    private String code;

    // Constructors
    public OtpVerifyDTO() {}

    public OtpVerifyDTO(String mobileNumber, String code) {
        this.mobileNumber = mobileNumber;
        this.code = code;
    }

    // Getters and Setters
    public String getMobileNumber() { return mobileNumber; }
    public void setMobileNumber(String mobileNumber) { this.mobileNumber = mobileNumber; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
}