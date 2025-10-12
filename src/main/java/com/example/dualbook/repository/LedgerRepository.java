package com.example.dualbook.repository;

import com.example.dualbook.entity.CurrencyType;
import com.example.dualbook.entity.Ledger;
import com.example.dualbook.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface LedgerRepository extends JpaRepository<Ledger, Long> {

    @Query("SELECT l FROM Ledger l WHERE ((l.user1 = :user1 AND l.user2 = :user2) OR (l.user1 = :user2 AND l.user2 = :user1)) AND l.currencyType = :currencyType AND l.disableDate IS NULL")
    Optional<Ledger> findByUsersAndCurrencyTypeAndActive(@Param("user1") User user1, @Param("user2") User user2, @Param("currencyType") CurrencyType currencyType);

    @Query("SELECT l FROM Ledger l WHERE (l.user1 = :user OR l.user2 = :user) AND l.disableDate IS NULL")
    List<Ledger> findAllActiveByUser(@Param("user") User user);

    @Query("SELECT l FROM Ledger l WHERE l.disableDate IS NULL")
    List<Ledger> findAllActive();
}