package com.senich.creditcheck.repository;

import com.senich.creditcheck.model.entity.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {

    void deleteByName(String name);
}
