package com.senich.creditcheck.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "portfolio_id_sequence")
    @SequenceGenerator(name = "portfolio_id_sequence", allocationSize = 1)
    private long id;

    private String name;

    private double maxFundValue;

    @OneToMany
    private List<Trade> trades = new ArrayList<>();

    @ManyToMany
    private List<Sector> securitySectors = new ArrayList<>();
}
