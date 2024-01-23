package com.senich.creditcheck.model.entity;

import com.senich.creditcheck.model.enums.SecurityType;
import com.senich.creditcheck.model.enums.TradeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "trade_id_sequence")
    @SequenceGenerator(name = "trade_id_sequence", allocationSize = 1)
    private long id;

    private String symbol;

    private double price;

    private long quantity;

    @ManyToOne
    private Sector sector;

    private SecurityType securityType;

    @Enumerated(EnumType.STRING)
    private TradeType tradeType;

}
