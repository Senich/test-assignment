package com.senich.creditcheck.model.entity;

import com.senich.creditcheck.model.enums.SectorType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("sector")
public class Sector {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sector_id_sequence")
    @SequenceGenerator(name = "sector_id_sequence", allocationSize = 1)
    private long id;

    @Enumerated(EnumType.STRING)
    private SectorType type;

    private double exposureLimit;
}
