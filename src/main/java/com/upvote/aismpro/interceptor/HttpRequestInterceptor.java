package com.upvote.aismpro.interceptor;

import com.upvote.aismpro.security.SecurityService;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class HttpRequestInterceptor implements HandlerInterceptor {

    @Autowired
    private SecurityService securityService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("===== preHandle method =====");

        // request header token decrypt 해서 권한 확인해주기
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (token != null) token = token.split(" ")[1];

        // 이거 빼면 CORS 에러 발생 : preflight 문제
        // prefilght로 보내지는 http method인 OPTIONS가 들어오면 그냥 통과
        // Access to XMLHttpRequest at 'http://localhost:8888/auth/getSongByOption?userID=8c451bf1-7224-3sc3-1112-854b032bbb4d&option=Create' from origin 'http://localhost:3000' has been blocked by CORS policy: Response to preflight request doesn't pass access control check: No 'Access-Control-Allow-Origin' header is present on the requested resource.
        if (StringUtils.equals(request.getMethod(), "OPTIONS")) {
            System.out.println("if request options method is options, return true");
            return true;
        }

        if (request.getRequestURI().contains("auth") == false)  {
            return true;
        }

        if (token == null || token.isEmpty() || !securityService.validateToken(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            System.out.println("사용자 인증 실패" + token);
            return false;
        } else {
            System.out.println("사용자 인증 성공");
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle method");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception arg3) throws Exception {
        System.out.println("afterCompletion method");
    }


}
