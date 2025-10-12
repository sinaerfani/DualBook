package com.example.dualbook.repository;


import com.example.dualbook.entity.CurrencyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CurrencyTypeRepository extends JpaRepository<CurrencyType, Long> {

    Optional<CurrencyType> findBySymbol(String symbol);

    @Query("SELECT c FROM CurrencyType c WHERE c.disableDate IS NULL")
    List<CurrencyType> findAllByDisableDateIsNull();

    @Query("SELECT c FROM CurrencyType c WHERE c.symbol = :symbol AND c.disableDate IS NULL")
    Optional<CurrencyType> findBySymbolAndActive(String symbol);
}