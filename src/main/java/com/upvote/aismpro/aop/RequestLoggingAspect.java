package com.upvote.aismpro.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Aspect
public class RequestLoggingAspect {

    @Pointcut("execution(* com.upvote.aismpro.controller..*.*(..))")
    private void anyRequest() {
    }

    @Around("anyRequest()")
    public Object aroundRequest(ProceedingJoinPoint pjp) throws Throwable {
        // HttpServletRequest 불러오기
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        // Client IP 불러오기
        String ip = request.getHeader("X-Forwarded-For");
        if (!StringUtils.hasText(ip)) {
            ip = request.getRemoteAddr();
        }

        // 응답시간 측정
        long start = System.currentTimeMillis();

        try {
            System.out.println();
            log.info("[{}] {}: {}",
                    ip,
                    request.getMethod(),
                    request.getRequestURL());

            return pjp.proceed(pjp.getArgs());
        } finally {
            long end = System.currentTimeMillis();
            log.info("[{}] {}ms",
                    ip,
                    end - start);
        }
    }
}
