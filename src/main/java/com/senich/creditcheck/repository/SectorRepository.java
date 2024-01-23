package com.senich.creditcheck.repository;

import com.senich.creditcheck.model.entity.Sector;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SectorRepository extends R2dbcRepository<Sector, Long> {
}
