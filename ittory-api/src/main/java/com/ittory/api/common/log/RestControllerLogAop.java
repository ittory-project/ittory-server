package com.ittory.api.common.log;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Aspect
@Component
public class RestControllerLogAop {

    @Pointcut("execution(* com.ittory.api.*.controller..*.*(..))")
    private void cut() { }

    @Around("cut()")
    public Object loggingAround(ProceedingJoinPoint joinPoint) throws Throwable {
        // 1. Request 로그
        logRequest(joinPoint);

        // 2. 메서드 실행 (여기서 쿼리 로그가 발생함)
        Object result = joinPoint.proceed();

        // 3. Return 로그
        logReturn(joinPoint, result);

        return result;
    }

    private void logRequest(JoinPoint joinPoint) {
        // Request URI
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            HttpServletRequest request = requestAttributes.getRequest();
            log.info("====== Requested URL: {} - {} ======", request.getMethod(), request.getRequestURI());
        } else {
            log.info("====== Requested URL: Unknown ======");
        }

        // 파라미터 로깅
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String[] parameterNames = signature.getParameterNames();
        Object[] args = joinPoint.getArgs();

        if (args.length <= 0) {
            log.info("Request Data: null");
        } else {
            for (int i = 0; i < parameterNames.length; i++) {
                String paramName = parameterNames[i];
                String paramType = args[i] != null ? args[i].getClass().getSimpleName() : "null";
                Object paramValue = args[i];
                log.info("Request Data [name: {}, type: {}, value: {}]", paramName, paramType, paramValue);
            }
        }
    }

    private void logReturn(JoinPoint joinPoint, Object returnObj) {
        // Response 로그
        log.info("Return: type = {}, Value = {}", returnObj.getClass().getSimpleName(), returnObj);
    }
}