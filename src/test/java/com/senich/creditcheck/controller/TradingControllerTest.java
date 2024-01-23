package com.senich.creditcheck.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senich.creditcheck.AbstractTest;
import com.senich.creditcheck.model.dto.TradeRequest;
import com.senich.creditcheck.model.dto.PortfolioData;
import com.senich.creditcheck.model.dto.Security;
import com.senich.creditcheck.model.entity.Portfolio;
import com.senich.creditcheck.model.enums.SectorType;
import com.senich.creditcheck.model.enums.SecurityType;
import com.senich.creditcheck.model.enums.TradeType;
import com.senich.creditcheck.service.ReferenceDataService;
import com.senich.creditcheck.service.PortfolioService;
import com.senich.creditcheck.service.inventory.InventoryService;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@Sql(scripts = "/sql/test_data.sql")
class TradingControllerTest extends AbstractTest {

    static final String TEST_URL = "/market/orders";
    public static final String AAPL = "AAPL";
    public static final String IBM = "IBM";

    @Autowired
    PortfolioService portfolioService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    InventoryService inventoryService;

    @Autowired
    ReferenceDataService referenceDataService;

    @ParameterizedTest
    @MethodSource("orderDataProvider")
    void testWhenExposureLimitForSectorIsNotExceeded(TradeType type, int orderQty, String result, int qtyBefore,
        int qtyAfter, int tradeSize, double sectorAmount, double portfolioTotal, long traderId) throws Exception {

        Security security = inventoryService.getSecurity(AAPL, SecurityType.STOCK);
        TradeRequest tradeRequest = createOrder(security, orderQty, type, traderId);

        //check inventory qty before
        assertEquals(qtyBefore, security.getAvailableQty().get());

        String responseResult = mockMvc.perform(post(TEST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tradeRequest)))
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();
        assertEquals(result, responseResult);

        //check inventory qty after
        assertEquals(qtyAfter, security.getAvailableQty().get());

        //check portfolio trades in database and total portfolio amount
        Portfolio portfolio = portfolioService.getPortfolio(1L);
        assertEquals(tradeSize, portfolio.getTrades().size());
        assertEquals(portfolioTotal, portfolio.getMaxFundValue());


        //check portfolio reference data
        PortfolioData data = referenceDataService.getPortfolioReferenceData(1L);
        assertEquals(sectorAmount, data.sectors().get(SectorType.INFORMATION_TECHNOLOGY).currentAmount().get());

    }

    static Stream<Arguments> orderDataProvider() {
        return Stream.of(
            // exposure limit for IT sector is 70000, initial trade total amount 55000, stock price is 20
            // side|order qty|status|inventory qty before|inventory qty after|sector total amount|portfolio total|trader id
            Arguments.of(TradeType.BUY, 1000, "Failed", 5000, 5000, 4, 55000.0, 70600.0, 1),
            Arguments.of(TradeType.BUY, 10, "Success", 5000, 4990, 5, 55200.0, 70800.0, 2),
            Arguments.of(TradeType.SELL, 750, "Success", 4990, 5740, 4, 40200.0, 55800.0, 1),
            Arguments.of(TradeType.BUY, 1500, "Failed", 5740, 5740, 5, 40200.0, 55800.0, 2)
        );
    }

    @Test
    void testWhenThereAreNotEnoughSecuritiesInInventory() throws Exception {
        Security security = inventoryService.getSecurity(IBM, SecurityType.OPTION);
        TradeRequest tradeRequest = createOrder(security, 750, TradeType.BUY);

        //check inventory qty before
        assertEquals(700, security.getAvailableQty().get());

        String responseResult = mockMvc.perform(post(TEST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tradeRequest)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andReturn()
            .getResponse()
            .getContentAsString();

        assertEquals("Insufficient security qty for IBM. Requested 750, available 700", responseResult);
    }

}
