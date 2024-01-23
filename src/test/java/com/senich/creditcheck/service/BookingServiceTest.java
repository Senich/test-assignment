package com.senich.creditcheck.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.senich.creditcheck.AbstractTest;
import com.senich.creditcheck.model.dto.TradeRequest;
import com.senich.creditcheck.model.dto.Security;
import com.senich.creditcheck.model.enums.SecurityType;
import com.senich.creditcheck.model.enums.TradeType;
import com.senich.creditcheck.repository.TradeRepository;
import com.senich.creditcheck.service.inventory.InventoryService;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

@Sql(scripts = "/sql/reset_trade.sql")
@Sql(scripts = "/sql/test_data.sql")
@Execution(ExecutionMode.CONCURRENT)
class BookingServiceTest extends AbstractTest {

    @Autowired
    BookingService bookingService;

    @Autowired
    TradeRepository tradeRepository;

    @Autowired
    InventoryService inventoryService;

    Security security;

    @BeforeEach
    void setUp() {
        security = inventoryService.getSecurity("AAPL", SecurityType.STOCK);
    }

    private static final AtomicInteger failureCount = new AtomicInteger(0);

    /*
        Current INFORMATION_TECHNOLOGY sector amount is 55000, Sector Limit is 70000
        we book 4 trades 5000 each which means only 3 trades should be successful
     */
    @Test
    void testBookOrder1() {
        TradeRequest tradeRequest = createOrder(security, 250, TradeType.BUY);
        runTest(tradeRequest);
    }

    @Test
    void testBookOrder2() {
        TradeRequest tradeRequest = createOrder(security, 250, TradeType.BUY);
        runTest(tradeRequest);
    }

    @Test
    void testBookOrder3() {
        TradeRequest tradeRequest = createOrder(security, 250, TradeType.BUY);
        runTest(tradeRequest);
    }

    @Test
    void testBookOrder4() {
        TradeRequest tradeRequest = createOrder(security, 250, TradeType.BUY);
        runTest(tradeRequest);
    }

    private void runTest(TradeRequest tradeRequest) {
        try {
            assertEquals("Success", bookingService.bookTrade(tradeRequest));
        } catch (AssertionError e) {
            failureCount.incrementAndGet();
        }
    }

    @AfterAll
    static void validateFailureCount() {
        assertEquals(1, failureCount.get(), "Exactly one test should fail");
    }

}
