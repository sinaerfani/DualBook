package com.example.dualbook.service.currencyType;


import com.example.dualbook.entity.CurrencyType;
import com.example.dualbook.repository.CurrencyTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class CurrencyTypeServiceImpl implements CurrencyTypeService {

    private final CurrencyTypeRepository currencyTypeRepository;

    public CurrencyTypeServiceImpl(CurrencyTypeRepository currencyTypeRepository) {
        this.currencyTypeRepository = currencyTypeRepository;
    }

    @Override
    public CurrencyType createCurrencyType(String symbol, String nameFa) {
        if (currencyTypeRepository.findBySymbol(symbol).isPresent()) {
            throw new RuntimeException("Currency type with this symbol already exists");
        }

        CurrencyType currencyType = new CurrencyType();
        currencyType.setSymbol(symbol);
        currencyType.setNameFa(nameFa);

        return currencyTypeRepository.save(currencyType);
    }

    @Override
    public CurrencyType findBySymbol(String symbol) {
        return currencyTypeRepository.findBySymbol(symbol)
                .orElseThrow(() -> new RuntimeException("Currency type not found"));
    }

    @Override
    public List<CurrencyType> getAllCurrencyTypes() {
        return currencyTypeRepository.findAllByDisableDateIsNull();
    }

    @Override
    public CurrencyType updateCurrencyType(Long id, String nameFa) {
        CurrencyType currencyType = currencyTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Currency type not found"));

        currencyType.setNameFa(nameFa);
        return currencyTypeRepository.save(currencyType);
    }

    @Override
    public void deleteCurrencyType(Long id) {
        CurrencyType currencyType = currencyTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Currency type not found"));

        currencyType.setDisableDate(LocalDateTime.now());
        currencyTypeRepository.save(currencyType);
    }
}
