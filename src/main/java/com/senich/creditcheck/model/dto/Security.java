package com.senich.creditcheck.model.dto;

import com.senich.creditcheck.model.enums.SectorType;
import com.senich.creditcheck.model.enums.SecurityType;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Security {
    private String symbol;
    private double price;
    private SecurityType securityType;
    private SectorType sectorType;
    private AtomicInteger availableQty;
}
