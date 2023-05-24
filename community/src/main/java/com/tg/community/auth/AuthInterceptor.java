package com.tg.community.auth;

import com.tg.community.auth.domain.AuthService;
import com.tg.community.auth.domain.SessionUserVo;
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
@Slf4j
@RequiredArgsConstructor
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
        if (session == null) {
            if (auth != null) {
                response.sendError(HttpStatus.UNAUTHORIZED.value(), "로그인이 필요합니다.");
                return false;
            } else {
                UserContext.CONTEXT.set(null);
                return true;
            }
        }

        log.info("sessionID: {}", session.getId());
        SessionUserVo sessionUserVo = authService.getSession(session.getId());
        if (sessionUserVo == null) {
            if (auth != null) {
                response.sendError(HttpStatus.UNAUTHORIZED.value(), "유효하지 않는 세션입니다.");
                return false;
            } else {
                UserContext.CONTEXT.set(null);
                return true;
            }
        }
        UserContext.CONTEXT.set(sessionUserVo);
        return true;
    }

}
