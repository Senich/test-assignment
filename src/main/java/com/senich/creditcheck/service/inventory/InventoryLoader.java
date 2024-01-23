package com.senich.creditcheck.service.inventory;

import com.opencsv.bean.CsvToBeanBuilder;
import com.senich.creditcheck.exceptions.ApplicationException;
import com.senich.creditcheck.model.dto.Security;
import com.senich.creditcheck.model.dto.SecurityCsv;
import com.senich.creditcheck.model.enums.SecurityType;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class InventoryLoader {

    @Value("${app.inventory.data.path}")
    private String dataPath;

    public Map<String, ConcurrentMap<SecurityType, Security>> loadData() {
        try {
            List<SecurityCsv> securitiesData = new CsvToBeanBuilder<SecurityCsv>(new FileReader(dataPath))
                .withType(SecurityCsv.class).build().parse();
            log.info("{} products has been load into inventory", securitiesData.size());
            return securitiesData
                .stream()
                .map(this::mapToSecurity)
                .collect(Collectors.groupingByConcurrent(
                    Security::getSymbol,
                    ConcurrentHashMap::new,
                    Collectors.toConcurrentMap(
                        Security::getSecurityType,
                        Function.identity(),
                        (existing, replacement) -> existing
                    )));
        } catch (FileNotFoundException e) {
            throw new ApplicationException("Inventory data loading failed", e);
        }
    }

    private Security mapToSecurity(SecurityCsv securityCsv) {
        return Security.builder()
            .availableQty(new AtomicInteger(securityCsv.getAvailableQty()))
            .securityType(securityCsv.getSecurityType())
            .sectorType(securityCsv.getSectorType())
            .symbol(securityCsv.getSymbol())
            .price(securityCsv.getPrice())
        .build();
    }
}
