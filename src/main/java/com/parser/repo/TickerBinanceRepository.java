package com.parser.repo;

import com.parser.entityBinance.TickerBinance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TickerBinanceRepository extends JpaRepository<TickerBinance,Long> {
}
