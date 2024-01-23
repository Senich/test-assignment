package com.senich.creditcheck.repository;

import com.senich.creditcheck.model.entity.Sector;
import com.senich.creditcheck.model.enums.SectorType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SectorRepository extends JpaRepository<Sector, Long> {

    Optional<Sector> findByTypeAndPortfolioId(SectorType type, long portfolioId);
}
