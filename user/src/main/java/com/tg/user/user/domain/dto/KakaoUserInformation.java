package com.tg.user.user.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class KakaoUserInformation {
    @JsonProperty(value = "id")
    private String providerId;

}
