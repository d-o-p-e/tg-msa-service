package com.tg.campaign.user.domain;

import jakarta.persistence.Embeddable;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Embeddable
//@NoArgsConstructor
public class Mileage {
    private int amounts;

    public Mileage(int amounts) {
        this.amounts = amounts;
    }

    public Mileage() {
    }

    public int inquiryAmounts() {
        return amounts;
    }

    public void verifyAtLeastBalance() {
         if(amounts <= 1) {
             throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "마일리지가 부족합니다.");
         }
    }
}
