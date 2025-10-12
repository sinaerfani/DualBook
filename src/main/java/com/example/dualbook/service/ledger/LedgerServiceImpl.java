package com.example.dualbook.service.ledger;

import com.example.dualbook.entity.CurrencyType;
import com.example.dualbook.entity.Ledger;
import com.example.dualbook.entity.User;
import com.example.dualbook.repository.CurrencyTypeRepository;
import com.example.dualbook.repository.LedgerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LedgerServiceImpl implements LedgerService {

    private final LedgerRepository ledgerRepository;
    private final CurrencyTypeRepository currencyTypeRepository;

    public LedgerServiceImpl(LedgerRepository ledgerRepository,
                             CurrencyTypeRepository currencyTypeRepository) {
        this.ledgerRepository = ledgerRepository;
        this.currencyTypeRepository = currencyTypeRepository;
    }

    @Override
    public Ledger findLedgerBetweenUsers(User user1, User user2, String currencySymbol) {
        CurrencyType currencyType = currencyTypeRepository.findBySymbol(currencySymbol)
                .orElseThrow(() -> new RuntimeException("Currency type not found"));

        return ledgerRepository.findByUsersAndCurrencyTypeAndActive(user1, user2, currencyType)
                .orElseThrow(() -> new RuntimeException("Ledger not found"));
    }

    @Override
    public List<Ledger> getUserLedgers(User user) {
        return ledgerRepository.findAllActiveByUser(user);
    }

    @Override
    public java.math.BigDecimal getUserTotalBalance(User user) {
        List<Ledger> userLedgers = getUserLedgers(user);
        return userLedgers.stream()
                .map(ledger -> {
                    // اگر کاربر user1 باشد، مانده مستقیم است
                    // اگر کاربر user2 باشد، مانده منفی است
                    if (ledger.getUser1().equals(user)) {
                        return ledger.getBalance();
                    } else {
                        return ledger.getBalance().negate();
                    }
                })
                .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add);
    }

    @Override
    public Ledger createLedger(User user1, User user2, String currencySymbol) {
        CurrencyType currencyType = currencyTypeRepository.findBySymbol(currencySymbol)
                .orElseThrow(() -> new RuntimeException("Currency type not found"));

        Ledger ledger = new Ledger();
        ledger.setUser1(user1);
        ledger.setUser2(user2);
        ledger.setCurrencyType(currencyType);
        ledger.setBalance(java.math.BigDecimal.ZERO);

        return ledgerRepository.save(ledger);
    }
}