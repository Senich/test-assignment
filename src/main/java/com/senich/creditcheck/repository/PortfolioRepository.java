package com.senich.creditcheck.repository;

import com.senich.creditcheck.model.entity.Portfolio;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PortfolioRepository extends R2dbcRepository<Portfolio, Long> {
}
