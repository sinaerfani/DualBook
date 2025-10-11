package com.example.dualbook.mapper;

import com.example.dualbook.dto.LedgerDTO;
import com.example.dualbook.entity.Ledger;
import org.springframework.stereotype.Component;

@Component
public class LedgerMapper {

    public LedgerDTO toDTO(Ledger ledger) {
        if (ledger == null) {
            return null;
        }

        LedgerDTO dto = new LedgerDTO();
        dto.setId(ledger.getId());
        dto.setUser1Name(ledger.getUser1().getFullName());
        dto.setUser2Name(ledger.getUser2().getFullName());
        dto.setCurrencySymbol(ledger.getCurrencyType().getSymbol());
        dto.setCurrencyNameFa(ledger.getCurrencyType().getNameFa());
        dto.setBalance(ledger.getBalance());

        return dto;
    }
}