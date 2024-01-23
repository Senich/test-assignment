package com.senich.creditcheck.model.entity;

import com.senich.creditcheck.model.enums.SecurityType;
import com.senich.creditcheck.model.enums.TradeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(of = "id")
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "trade_id_sequence")
    @SequenceGenerator(name = "trade_id_sequence", allocationSize = 1)
    private long id;

    private String symbol;

    private double price;

    private int quantity;

    @ManyToOne
    @JoinColumn(name = "sector_id")
    private Sector sector;

    @Enumerated(EnumType.STRING)
    private SecurityType securityType;

    @Enumerated(EnumType.STRING)
    private TradeType tradeType;

    @ManyToOne
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

    @ManyToOne
    @JoinColumn(name = "trader_id")
    private Trader trader;

}
