package com.senich.creditcheck.model.dto;

import com.senich.creditcheck.model.enums.SectorType;
import java.util.Map;

public record PortfolioData(Map<SectorType, SectorData> sectors) {
}
