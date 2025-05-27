package com.ittory.socket.common.log;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

@Slf4j
@Aspect
@Component
public class StompMessageLogAop {

    @Pointcut("@annotation(org.springframework.messaging.handler.annotation.MessageMapping)")
    private void messageMapping() { }

    @Around("messageMapping()")
    public Object loggingAround(ProceedingJoinPoint joinPoint) throws Throwable {
        // 1. Request 로그
        logRequest(joinPoint);

        // 2. 메서드 실행 (여기서 쿼리 로그가 발생함)
        Object result = joinPoint.proceed();

        // 3. Return 로그
        logReturn(joinPoint, result);

        return result;
    }

    private void logRequest(ProceedingJoinPoint joinPoint) {
        try {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();

            // MessageMapping 어노테이션 값 추출
            MessageMapping messageMapping = method.getAnnotation(MessageMapping.class);
            String destination = messageMapping != null && messageMapping.value().length > 0 ?
                    messageMapping.value()[0] : "Unknown";

            log.info("====== STOMP Message Received: {} ======", destination);

            // 파라미터 로깅
            Object[] args = joinPoint.getArgs();
            Parameter[] parameters = method.getParameters();
            Annotation[][] parameterAnnotations = method.getParameterAnnotations();

            for (int i = 0; i < args.length; i++) {
                String paramName = parameters[i].getName();
                Object paramValue = args[i];
                String paramType = paramValue != null ? paramValue.getClass().getSimpleName() : "null";

                // DestinationVariable 어노테이션이 있는 경우 해당 값도 로깅
                for (Annotation annotation : parameterAnnotations[i]) {
                    if (annotation instanceof DestinationVariable) {
                        paramName = "DestinationVariable:" + paramName;
                        break;
                    }
                }

                // StompHeaderAccessor인 경우 세션 ID 추출
                if (paramValue instanceof StompHeaderAccessor) {
                    StompHeaderAccessor accessor = (StompHeaderAccessor) paramValue;
                    log.info("STOMP Message Data [name: {}, type: {}, sessionId: {}]",
                            paramName, paramType, accessor.getSessionId());
                } else {
                    log.info("STOMP Message Data [name: {}, type: {}, value: {}]",
                            paramName, paramType, paramValue);
                }
            }
        } catch (Exception e) {
            log.error("Error logging STOMP message request", e);
        }
    }

    private void logReturn(ProceedingJoinPoint joinPoint, Object returnObj) {
        if (returnObj != null) {
            log.info("STOMP Message Return: type = {}, Value = {}",
                    returnObj.getClass().getSimpleName(), returnObj);
        } else {
            log.info("STOMP Message Return: void (no return value)");
        }
    }
}