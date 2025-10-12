package com.example.dualbook.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ledgers", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user1_id", "user2_id", "currency_type_id"})
})
public class Ledger extends BaseEntity {
    private User user1;
    private User user2;
    private CurrencyType currencyType;
    private BigDecimal balance = BigDecimal.ZERO;
    private List<Transaction> transactions = new ArrayList<>();


    // Getters and Setters

    @ManyToOne
    @JoinColumn(name = "currency_type_id", nullable = false)
    public CurrencyType getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(CurrencyType currencyType) {
        this.currencyType = currencyType;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @ManyToOne
    @JoinColumn(name = "user1_id", nullable = false)
    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    @ManyToOne
    @JoinColumn(name = "user2_id", nullable = false)
    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }

    @Column(precision = 15, scale = 6)
    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @OneToMany(mappedBy = "ledger", cascade = CascadeType.ALL)
    public List<Transaction> getTransactions() {
        return transactions;
    }
}

