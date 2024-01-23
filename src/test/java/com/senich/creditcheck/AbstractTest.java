package com.senich.creditcheck;

import com.senich.creditcheck.model.dto.Security;
import com.senich.creditcheck.model.dto.TradeRequest;
import com.senich.creditcheck.model.enums.TradeType;
import com.senich.creditcheck.repository.PortfolioRepository;
import com.senich.creditcheck.repository.SectorRepository;
import com.senich.creditcheck.repository.TradeRepository;
import com.senich.creditcheck.repository.TraderRepository;
import com.senich.creditcheck.testcontainers.TestContainers;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public abstract class AbstractTest {

    @Autowired
    SectorRepository sectorRepository;

    @Autowired
    PortfolioRepository portfolioRepository;

    @Autowired
    TradeRepository tradeRepository;

    @Autowired
    TraderRepository traderRepository;

    @BeforeAll
    static void startTestContainers() {
        TestContainers.startPostgres();
    }

    protected void clearDb() {
        List.of(
            tradeRepository,
            traderRepository,
            sectorRepository,
            portfolioRepository
        ).forEach(CrudRepository::deleteAll);
    }

    protected TradeRequest createOrder(Security security, int qty, TradeType tradeType) {
        return createOrder(security, qty, tradeType, 1L);
    }

    protected TradeRequest createOrder(Security security, int qty, TradeType tradeType, long traderId) {
        return TradeRequest.builder()
            .symbol(security.getSymbol())
            .portfolioId(1L)
            .price(security.getPrice())
            .quantity(qty)
            .traderId(traderId)
            .securityType(security.getSecurityType())
            .tradeType(tradeType)
            .tradeId(1L)
            .build();
    }

}
