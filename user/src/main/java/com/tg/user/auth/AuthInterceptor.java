package com.tg.user.auth;

import com.tg.user.auth.domain.AuthService;
import com.tg.user.auth.domain.SessionUserVo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
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
        if (session == null) {
            if (auth != null) {
                response.sendError(HttpStatus.UNAUTHORIZED.value(), "로그인이 필요합니다.");
                return false;
            } else {
                UserContext.CONTEXT.set(null);
                return true;
            }
        }

        SessionUserVo sessionUserVo = authService.getSession(session.getId());
        UserContext.CONTEXT.set(sessionUserVo);
        authService.extendExpiration(session.getId());
        return true;
    }

}
