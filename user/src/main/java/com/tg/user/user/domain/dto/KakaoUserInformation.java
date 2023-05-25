package com.tg.user.user.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
public class KakaoUserInformation {
    @JsonProperty(value = "id")
    private String providerId;
    private KakaoOAuthProperties properties;
    private KakaoOAuthAccountInfo kakao_account;

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @Getter
    public static class KakaoOAuthProperties {
        private String nickname;
        @JsonProperty(value = "profile_image")
        private String profileImage;
        @JsonProperty(value = "thumbnail_image")
        private String thumbnailImage;
        @JsonProperty(value = "kakao_account")
        private String kakaoAccount;
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @Getter
    public static class KakaoOAuthAccountInfo {
        private String email;
    }

}
