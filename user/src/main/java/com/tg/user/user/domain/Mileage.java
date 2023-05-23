package com.tg.user.user.domain;

import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
public class Mileage {
    private int amounts;

    public Mileage(int amounts) {
        this.amounts = amounts;
    }

    public int inquiryAmounts() {
        return amounts;
    }
}
