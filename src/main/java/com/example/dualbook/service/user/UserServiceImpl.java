package com.example.dualbook.service.user;

import com.example.dualbook.entity.User;
import com.example.dualbook.entity.enums.RoleName;
import com.example.dualbook.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User registerUser(String mobileNumber, String password, String fullName) {
        if (existsByMobileNumber(mobileNumber)) {
            throw new RuntimeException("User with this mobile number already exists");
        }

        User user = new User();
        user.setMobileNumber(mobileNumber);
        user.setPassword(passwordEncoder.encode(password));
        user.setFullName(fullName);
        user.setRole(RoleName.ROLE_USER);

        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByMobileNumber(String mobileNumber) {
        return userRepository.findByMobileNumberAndDisabledDateIsNull(mobileNumber);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id)
                .filter(user -> user.getDisableDate() == null);
    }

    @Override
    public List<User> findAllActiveUsers() {
        return userRepository.findAllActiveUsers();
    }

    @Override
    public List<User> findAllInactiveUsers() {
        return userRepository.findAllInactiveUsers();
    }

    @Override
    public void disableUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        if (user.getDisableDate() != null) {
            throw new RuntimeException("User is already disabled");
        }

        user.setDisableDate(LocalDateTime.now());
        userRepository.save(user);
    }

    @Override
    public void enableUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        user.setDisableDate(null);
        userRepository.save(user);
    }

    @Override
    public void changeUserRole(Long userId, RoleName newRole) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        if (user.getDisableDate() != null) {
            throw new RuntimeException("Cannot change role for disabled user");
        }

        user.setRole(newRole);
        userRepository.save(user);
    }

    @Override
    public User updateUser(Long userId, String fullName) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        if (user.getDisableDate() != null) {
            throw new RuntimeException("Cannot update disabled user");
        }

        user.setFullName(fullName);
        return userRepository.save(user);
    }

    @Override
    public void changePassword(Long userId, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        if (user.getDisableDate() != null) {
            throw new RuntimeException("Cannot change password for disabled user");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    public boolean existsByMobileNumber(String mobileNumber) {
        return userRepository.existsByMobileNumberAndDisabledDateIsNull(mobileNumber);
    }


}