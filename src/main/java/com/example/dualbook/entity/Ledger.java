package com.example.dualbook.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ledgers", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user1_id", "user2_id", "currency_type"})
})
public class Ledger extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "user1_id", nullable = false)
    private User user1;

    @ManyToOne
    @JoinColumn(name = "user2_id", nullable = false)
    private User user2;

    @ManyToOne
    @JoinColumn(name = "currency_type", nullable = false)
    private CurrencyType currencyType;

    @Column(precision = 15, scale = 6)
    private BigDecimal balance = BigDecimal.ZERO;



    @OneToMany(mappedBy = "ledger", cascade = CascadeType.ALL)
    private List<Transaction> transactions = new ArrayList<>();




    // Getters and Setters


    public CurrencyType getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(CurrencyType currencyType) {
        this.currencyType = currencyType;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public User getUser1() { return user1; }
    public void setUser1(User user1) { this.user1 = user1; }

    public User getUser2() { return user2; }
    public void setUser2(User user2) { this.user2 = user2; }


    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }


    public List<Transaction> getTransactions() { return transactions; }
}

