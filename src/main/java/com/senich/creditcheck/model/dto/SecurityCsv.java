package com.senich.creditcheck.model.dto;

import com.opencsv.bean.CsvBindByPosition;
import com.senich.creditcheck.model.enums.SectorType;
import com.senich.creditcheck.model.enums.SecurityType;
import lombok.Data;

@Data
public class SecurityCsv {
    @CsvBindByPosition(position = 0)
    private String symbol;
    @CsvBindByPosition(position = 1)
    private SecurityType securityType;
    @CsvBindByPosition(position = 2)
    private SectorType sectorType;
    @CsvBindByPosition(position = 3)
    private int availableQty;
    @CsvBindByPosition(position = 4)
    private double price;
}
