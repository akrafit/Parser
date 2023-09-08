package com.parser.repo;

import com.parser.entityYoBit.PairYobit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PairYobitRepository extends JpaRepository<PairYobit, Long> {
    @Query(value = "SELECT * FROM pair_yobit WHERE code LIKE '%_BTC'",
            nativeQuery = true)
    List<PairYobit> getPairYobitByCode();
}
