package com.example.dualbook.dto;

import java.math.BigDecimal;

public class LedgerDTO {
    private Long id;
    private String user1Name;
    private String user2Name;
    private String currencySymbol;
    private BigDecimal balance;
    private String currencyNameFa;

    // Constructors
    public LedgerDTO() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUser1Name() { return user1Name; }
    public void setUser1Name(String user1Name) { this.user1Name = user1Name; }

    public String getUser2Name() { return user2Name; }
    public void setUser2Name(String user2Name) { this.user2Name = user2Name; }

    public String getCurrencySymbol() { return currencySymbol; }
    public void setCurrencySymbol(String currencySymbol) { this.currencySymbol = currencySymbol; }

    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }

    public String getCurrencyNameFa() { return currencyNameFa; }
    public void setCurrencyNameFa(String currencyNameFa) { this.currencyNameFa = currencyNameFa; }
}