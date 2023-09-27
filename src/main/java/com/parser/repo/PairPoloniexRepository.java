package com.parser.repo;

import com.parser.entityPoloniex.PairPoloniex;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PairPoloniexRepository extends JpaRepository<PairPoloniex, Long> {
    List<PairPoloniex> findByCodeIs(String code);
}
