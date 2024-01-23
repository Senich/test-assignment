package com.senich.creditcheck.service;

import com.senich.creditcheck.exceptions.ApplicationException;
import com.senich.creditcheck.model.entity.Sector;
import com.senich.creditcheck.model.enums.SectorType;
import com.senich.creditcheck.repository.SectorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SectorService {

    private final SectorRepository sectorRepository;

    public Sector findSector(SectorType type, long portfolioId) {
        return sectorRepository.findByTypeAndPortfolioId(type, portfolioId)
            .orElseThrow(
                () -> new ApplicationException(
                    String.format("Portfolio id:%d is not allowed for trading in Sector %s", portfolioId, type)));
    }
}
