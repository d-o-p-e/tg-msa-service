package com.tg.user.user.controller;

import com.tg.user.auth.UserContext;
import com.tg.user.auth.domain.SessionUserVo;
import com.tg.user.user.domain.UserService;
import com.tg.user.user.domain.dto.UserInformationResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "USER", description = "유저 API")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "유저 정보 조회", description = "특정 유저의 정보를 조회합니다. (빈 값이면 현재 로그인한 유저의 정보를 조회합니다.)")
    @GetMapping("/{targetUserId}")
    public ResponseEntity<UserInformationResponseDto> getUserInfo(@PathVariable Long targetUserId) {
        if (targetUserId == 0) {
            SessionUserVo context = UserContext.getContext();
            if(context == null) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            targetUserId = context.getId();
        }
        return ResponseEntity.ok().body(userService.getUserInformation(targetUserId));
    }
}
