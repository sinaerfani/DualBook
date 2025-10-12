package com.example.dualbook.controller.api;

import com.example.dualbook.dto.*;
import com.example.dualbook.entity.User;
import com.example.dualbook.mapper.UserMapper;
import com.example.dualbook.service.otp.OtpService;
import com.example.dualbook.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthApiController {

    private final OtpService otpService;
    private final UserService userService;
    private final UserMapper userMapper;

    public AuthApiController(OtpService otpService, UserService userService, UserMapper userMapper) {
        this.otpService = otpService;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping("/otp/request")
    public ResponseEntity<ApiResponse<String>> requestOtp(@Valid @RequestBody OtpRequestDTO request) {
        boolean sent = otpService.generateAndSendOtp(request.getMobileNumber());

        if (sent) {
            return ResponseEntity.ok(ApiResponse.success("OTP sent successfully"));
        } else {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("OTP request limit exceeded. Try again tomorrow."));
        }
    }

    @PostMapping("/otp/verify")
    public ResponseEntity<ApiResponse<String>> verifyOtp(@Valid @RequestBody OtpVerifyDTO request) {
        boolean verified = otpService.verifyOtp(request.getMobileNumber(), request.getCode());

        if (verified) {
            return ResponseEntity.ok(ApiResponse.success("OTP verified successfully"));
        } else {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Invalid or expired OTP code"));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserDTO>> register(@Valid @RequestBody UserRegistrationDTO registrationDTO) {
        try {
            User user = userService.registerUser(
                    registrationDTO.getMobileNumber(),
                    registrationDTO.getFullName(),
                    registrationDTO.getOtpCode()
            );
            return ResponseEntity.ok(ApiResponse.success("Registration successful", userMapper.toDTO(user)));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Registration failed: " + e.getMessage()));
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<UserDTO>> getProfile(@AuthenticationPrincipal User user) {
        UserDTO userDTO = userMapper.toDTO(user);
        return ResponseEntity.ok(ApiResponse.success("Profile retrieved successfully", userDTO));
    }
}