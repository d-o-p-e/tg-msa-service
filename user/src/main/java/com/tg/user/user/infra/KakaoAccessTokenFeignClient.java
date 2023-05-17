package com.tg.user.user.infra;

import com.tg.user.user.domain.KakaoAccessTokenClient;
import com.tg.user.user.domain.dto.KakaoAccessTokenResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "KakaoAccessTokenClient", url = "${oauth.kakao.api_url.token}")
public interface KakaoAccessTokenFeignClient extends KakaoAccessTokenClient {

    @PostMapping(consumes = "application/json", produces = "application/json")
    KakaoAccessTokenResponseDto call(
            @RequestHeader("Content-Type") String contentType,
            @RequestParam("grant_type") String grantType,
            @RequestParam("client_id") String clientId,
            @RequestParam("redirect_uri") String redirectUri,
            @RequestParam("code") String code
    );
}
