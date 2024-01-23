package com.senich.creditcheck.repository;

import com.senich.creditcheck.model.entity.Trader;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TraderRepository extends R2dbcRepository<Trader, Long> {
}
