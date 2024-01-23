package com.senich.creditcheck.model.entity;

import com.senich.creditcheck.model.enums.SectorType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Sector {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sector_id_sequence")
    @SequenceGenerator(name = "sector_id_sequence", allocationSize = 1)
    private long id;

    @Enumerated(EnumType.STRING)
    private SectorType type;

    private double exposureLimit;

    @ManyToOne
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;
}
