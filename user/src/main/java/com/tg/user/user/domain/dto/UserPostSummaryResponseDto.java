package com.tg.user.user.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserPostSummaryResponseDto {
    private Long workoutCount;
    private Long algorithmCount;
    private Long earlyBirdCount;

    @Builder
    public UserPostSummaryResponseDto(Long workoutCount, Long algorithmCount, Long earlyBirdCount) {
        this.workoutCount = workoutCount;
        this.algorithmCount = algorithmCount;
        this.earlyBirdCount = earlyBirdCount;
    }
}
