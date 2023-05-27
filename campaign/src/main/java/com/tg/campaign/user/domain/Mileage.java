package com.tg.campaign.user.domain;

import jakarta.persistence.Embeddable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Embeddable
@Slf4j
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
         if(amounts < 1) {
             log.info("마일리지가 부족합니다.");
             throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "마일리지가 부족합니다.");
         }
    }
}
