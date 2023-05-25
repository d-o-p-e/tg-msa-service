package com.tg.campaign.auth;

import com.tg.campaign.auth.domain.AuthService;
import com.tg.campaign.auth.domain.SessionUserVo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {

    private final AuthService authService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (ResourceHttpRequestHandler.class.isAssignableFrom(handler.getClass())) {
            return true; // spring docs를 위한 처리
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Auth auth = handlerMethod.getMethodAnnotation(Auth.class);

        HttpSession session = request.getSession();
        if (session == null) { // 인증된 유저만.
            if (auth != null) {
                response.sendError(HttpStatus.UNAUTHORIZED.value(), "로그인이 필요합니다.");
                return false;
            } else { // 비로그인 유저 Null 처리
                UserContext.CONTEXT.set(null);
                return true;
            }
        }

        SessionUserVo sessionUserVo = authService.getSession(session.getId());
        if (sessionUserVo == null) { // 세션 만료
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "유효하지 않는 세션입니다.");
            return false;
        }
        log.info("유저 로그인 정보: {}", sessionUserVo);
        UserContext.CONTEXT.set(sessionUserVo);
        return true;
    }
}
