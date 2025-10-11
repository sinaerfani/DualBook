package com.example.dualbook.service.user;

import com.example.dualbook.entity.User;
import com.example.dualbook.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    // رمز عبور dummy برای کاربران OTP-based
    private final String DUMMY_PASSWORD = "OTP-BASED-AUTH-NO-PASSWORD";

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String mobileNumber) throws UsernameNotFoundException {

        User user = userRepository.findByMobileNumberAndDisabledDateIsNull(mobileNumber)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with mobile number: " + mobileNumber));

        // ایجاد نقش‌های کاربر
        List<GrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority(user.getRole().name())
        );

        // استفاده از رمز عبور dummy - در سیستم OTP-based این رمز عبور استفاده نمی‌شود
        // اما Spring Security به یک password نیاز دارد
        return new org.springframework.security.core.userdetails.User(
                user.getMobileNumber(),
                DUMMY_PASSWORD, // استفاده از رمز عبور ثابت
                true, // enabled
                true, // accountNonExpired
                true, // credentialsNonExpired
                user.getDisableDate() == null, // accountNonLocked (فعال بودن کاربر)
                authorities
        );
    }
}