package com.tg.user.user.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public class Mileage {
    private int amounts;

    public Mileage() {
        amounts = 0;
    }
}
