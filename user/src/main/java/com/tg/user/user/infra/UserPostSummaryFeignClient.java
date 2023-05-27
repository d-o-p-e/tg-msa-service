package com.tg.user.user.infra;

import com.tg.user.user.domain.dto.UserPostSummaryResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "UserPostSummaryClient", url = "${module.community.url}")
public interface UserPostSummaryFeignClient {
    @GetMapping(value = "/api/community/user-post-summary/{userId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    UserPostSummaryResponseDto call(@PathVariable("userId") Long userId);
}

