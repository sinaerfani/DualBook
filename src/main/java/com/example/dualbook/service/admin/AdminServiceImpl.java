package com.example.dualbook.service.admin;

import com.example.dualbook.entity.Ledger;
import com.example.dualbook.entity.Transaction;
import com.example.dualbook.entity.User;
import com.example.dualbook.entity.enums.RoleName;
import com.example.dualbook.entity.enums.TransactionStatus;
import com.example.dualbook.repository.LedgerRepository;
import com.example.dualbook.repository.TransactionRepository;
import com.example.dualbook.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final LedgerRepository ledgerRepository;

    public AdminServiceImpl(UserRepository userRepository,
                            TransactionRepository transactionRepository,
                            LedgerRepository ledgerRepository) {
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
        this.ledgerRepository = ledgerRepository;
    }

    @Override
    public long getUsersCount() {
        return userRepository.count();
    }

    @Override
    public long getActiveUsersCount() {
        return userRepository.findAllActiveUsers().size();
    }

    @Override
    public long getInactiveUsersCount() {
        return userRepository.findAllInactiveUsers().size();
    }

    @Override
    public long getTransactionsCount() {
        return transactionRepository.count();
    }

    @Override
    public long getLedgersCount() {
        return ledgerRepository.count();
    }

    @Override
    public long getPendingTransactionsCount() {
        // اصلاح: باید تمام تراکنش‌های pending را بشماریم
        return transactionRepository.findAll().stream()
                .filter(t -> t.getStatus() == TransactionStatus.PENDING && t.getDisableDate() == null)
                .count();
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> getUsersByRole(RoleName role) {
        return userRepository.findByRoleAndDisableDateIsNull(role);
    }

    @Override
    public void toggleUserStatus(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        if (user.getDisableDate() == null) {
            user.setDisableDate(LocalDateTime.now());
        } else {
            user.setDisableDate(null);
        }

        userRepository.save(user);
    }

    @Override
    public void changeUserRole(Long userId, RoleName newRole) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        user.setRole(newRole);
        userRepository.save(user);
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAllActive();
    }

    @Override
    public List<Ledger> getAllLedgers() {
        return ledgerRepository.findAllActive();
    }

    @Override
    public List<User> searchUsers(String keyword) {
        return userRepository.findByFullNameContainingAndDisableDateIsNull(keyword);
    }
}