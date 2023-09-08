package com.parser.repo;

import com.parser.entityBybit.TickerBybit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TickerBybitRepository extends JpaRepository<TickerBybit, Long> {
}
