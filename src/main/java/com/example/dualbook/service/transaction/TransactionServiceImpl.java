package com.example.dualbook.service.transaction;

import com.example.dualbook.dto.TransactionCreateDTO;
import com.example.dualbook.dto.TransactionResponseDTO;
import com.example.dualbook.entity.*;
import com.example.dualbook.entity.enums.TransactionStatus;
import com.example.dualbook.entity.enums.TransactionType;
import com.example.dualbook.repository.LedgerRepository;
import com.example.dualbook.repository.TransactionRepository;
import com.example.dualbook.repository.UserRepository;
import com.example.dualbook.service.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final LedgerRepository ledgerRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public TransactionServiceImpl(TransactionRepository transactionRepository,
                                  LedgerRepository ledgerRepository,
                                  UserRepository userRepository,
                                  UserService userService) {
        this.transactionRepository = transactionRepository;
        this.ledgerRepository = ledgerRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public Transaction createTransaction(User sender, TransactionCreateDTO transactionDTO) {
        // بررسی وجود تراکنش در انتظار
        User receiver = userService.findByMobileNumber(transactionDTO.getReceiverMobile())
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        if (hasPendingTransactionBetweenUsers(sender, receiver)) {
            throw new RuntimeException("Pending transaction already exists between users");
        }

        // پیدا کردن یا ایجاد دفتر
        CurrencyType currencyType = new CurrencyType(); // باید از ریپازیتوری مربوطه پیدا شود
        currencyType.setSymbol(transactionDTO.getCurrencySymbol());

        Ledger ledger = ledgerRepository.findByUsersAndCurrencyTypeAndActive(sender, receiver, currencyType)
                .orElseGet(() -> createNewLedger(sender, receiver, currencyType));

        // ایجاد تراکنش
        Transaction transaction = new Transaction();
        transaction.setLedger(ledger);
        transaction.setSender(sender);
        transaction.setReceiver(receiver);
        transaction.setType(transactionDTO.getType());
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setDescription(transactionDTO.getDescription());
        transaction.setDueDate(transactionDTO.getDueDate());
        transaction.setStatus(TransactionStatus.PENDING);

        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction confirmTransaction(Long transactionId, User receiver) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        if (!transaction.getReceiver().equals(receiver)) {
            throw new RuntimeException("Only receiver can confirm transaction");
        }

        if (transaction.getStatus() != TransactionStatus.PENDING) {
            throw new RuntimeException("Transaction is not in pending status");
        }

        transaction.setStatus(TransactionStatus.CONFIRMED);
        transaction.setConfirmedAt(LocalDateTime.now());

        // به‌روزرسانی مانده دفتر
        updateLedgerBalance(transaction);

        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction rejectTransaction(Long transactionId, User receiver, String reason) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        if (!transaction.getReceiver().equals(receiver)) {
            throw new RuntimeException("Only receiver can reject transaction");
        }

        if (transaction.getStatus() != TransactionStatus.PENDING) {
            throw new RuntimeException("Transaction is not in pending status");
        }

        transaction.setStatus(TransactionStatus.REJECTED);
        transaction.setDescription(transaction.getDescription() + " - Rejected: " + reason);

        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction cancelTransaction(Long transactionId, User sender) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        if (!transaction.getSender().equals(sender)) {
            throw new RuntimeException("Only sender can cancel transaction");
        }

        if (transaction.getStatus() != TransactionStatus.PENDING) {
            throw new RuntimeException("Only pending transactions can be cancelled");
        }

        transaction.setDisableDate(LocalDateTime.now());
        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction findById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
    }

    @Override
    public List<Transaction> getUserTransactions(User user) {
        return transactionRepository.findByUserAndStatusAndActive(user, null);
    }

    @Override
    public List<Transaction> getPendingTransactions(User user) {
        return transactionRepository.findByUserAndStatusAndActive(user, TransactionStatus.PENDING);
    }

    @Override
    public Transaction createReturnTransaction(User sender, Long originalTransactionId, String description) {
        Transaction originalTransaction = findById(originalTransactionId);

        if (originalTransaction.getStatus() != TransactionStatus.CONFIRMED) {
            throw new RuntimeException("Only confirmed transactions can be returned");
        }

        Transaction returnTransaction = new Transaction();
        returnTransaction.setLedger(originalTransaction.getLedger());
        returnTransaction.setSender(sender);
        returnTransaction.setReceiver(originalTransaction.getSender());
        returnTransaction.setType(TransactionType.RETURN);
        returnTransaction.setAmount(originalTransaction.getAmount());
        returnTransaction.setDescription(description);
        returnTransaction.setReferenceTransaction(originalTransaction);
        returnTransaction.setStatus(TransactionStatus.PENDING);

        return transactionRepository.save(returnTransaction);
    }

    @Override
    public boolean hasPendingTransactionBetweenUsers(User user1, User user2) {
        return transactionRepository.countPendingActiveTransactionsBetweenUsers(user1, user2) > 0;
    }

    @Override
    public List<Transaction> getTransactionsByStatus(User user, TransactionStatus status) {
        return transactionRepository.findByUserAndStatusAndActive(user, status);
    }

    private Ledger createNewLedger(User user1, User user2, CurrencyType currencyType) {
        Ledger ledger = new Ledger();
        ledger.setUser1(user1);
        ledger.setUser2(user2);
        ledger.setCurrencyType(currencyType);
        ledger.setBalance(java.math.BigDecimal.ZERO);
        return ledgerRepository.save(ledger);
    }

    private void updateLedgerBalance(Transaction transaction) {
        Ledger ledger = transaction.getLedger();
        java.math.BigDecimal currentBalance = ledger.getBalance();

        switch (transaction.getType()) {
            case DEBIT_NOTE:
                // فرستنده به گیرنده بدهکار می‌شود
                ledger.setBalance(currentBalance.add(transaction.getAmount()));
                break;
            case REPAYMENT:
            case RETURN:
                // فرستنده به گیرنده بستانکار می‌شود
                ledger.setBalance(currentBalance.subtract(transaction.getAmount()));
                break;
        }

        ledgerRepository.save(ledger);
    }
}