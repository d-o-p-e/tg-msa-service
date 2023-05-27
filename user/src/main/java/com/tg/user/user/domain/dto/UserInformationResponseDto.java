package com.tg.user.user.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class UserInformationResponseDto {
    private Long userId;
    private String nickname;
    private String profileImage;
    private Long workoutCount;
    private Long algorithmCount;
    private Long earlyBirdCount;

    @Builder
    public UserInformationResponseDto(Long userId, String nickname, String profileImage, Long workoutCount, Long algorithmCount, Long earlyBirdCount) {
        this.userId = userId;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.workoutCount = workoutCount;
        this.algorithmCount = algorithmCount;
        this.earlyBirdCount = earlyBirdCount;
    }
}
