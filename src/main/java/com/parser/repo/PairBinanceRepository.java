package com.parser.repo;

import com.parser.entityBinance.PairBinance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PairBinanceRepository extends JpaRepository<PairBinance,Long> {
    @Query(value = "SELECT * FROM pair_binance WHERE code LIKE '%_BTC' AND status = 'TRADING'",
            nativeQuery = true)
    List<PairBinance> getPairBinanceByCode();
    List<PairBinance> findByCodeIs(String code);
}
