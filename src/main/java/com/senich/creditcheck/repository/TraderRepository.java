package com.senich.creditcheck.repository;

import com.senich.creditcheck.model.entity.Trader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TraderRepository extends JpaRepository<Trader, Long> {
}
