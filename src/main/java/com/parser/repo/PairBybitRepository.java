package com.parser.repo;

import com.parser.entityBybit.PairBybit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PairBybitRepository extends JpaRepository<PairBybit, Long> {
    List<PairBybit> findByCodeIs(String code);
}
