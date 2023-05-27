package com.tg.campaign.campaign.domain.dto;

import lombok.Getter;

@Getter
public class UserMileageResponseDto {
    int amount;

    public UserMileageResponseDto(int amount) {
        this.amount = amount;
    }
}
