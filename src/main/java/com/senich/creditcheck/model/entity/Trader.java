package com.senich.creditcheck.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import java.util.List;
import lombok.Data;

@Entity
@Data
public class Trader {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "trader_id_sequence")
    @SequenceGenerator(name = "trader_id_sequence", allocationSize = 1)
    private long id;

    private String name;

    @ManyToMany
    private List<Portfolio> portfolios;

}
