package com.senich.creditcheck.model.dto;

import com.senich.creditcheck.model.enums.SectorType;
import lombok.Data;

@Data
public class Security {

    private String symbol;
    private SectorType securityType;
    private SectorType sectorType;
    private long availableQty;
}
