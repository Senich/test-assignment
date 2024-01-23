package com.senich.creditcheck.model.dto;

import com.google.common.util.concurrent.AtomicDouble;

public record SectorData(double exposureLimit, AtomicDouble currentAmount) {
}
