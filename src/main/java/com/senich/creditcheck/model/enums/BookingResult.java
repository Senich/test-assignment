package com.senich.creditcheck.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum BookingResult {
    FAILED("Failed"),
    SUCCESS("Success");

    @Getter
    private final String status;

}
