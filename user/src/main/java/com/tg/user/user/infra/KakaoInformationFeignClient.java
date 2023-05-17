package com.tg.user.user.infra;

import com.tg.user.user.domain.KakaoInformationClient;
import com.tg.user.user.domain.dto.KakaoUserInformation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "KakaoInformationClient", url = "${oauth.kakao.api_url.information}")
public interface KakaoInformationFeignClient extends KakaoInformationClient {

    @GetMapping
    KakaoUserInformation call(
            @RequestHeader("Content-Type") String contentType,
            @RequestHeader("Authorization") String accessToken
    );
}
