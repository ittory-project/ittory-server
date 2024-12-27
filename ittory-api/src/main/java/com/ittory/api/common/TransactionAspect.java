package com.ittory.api.common;

import com.ittory.domain.common.LazyLoadingDetector;
import com.ittory.infra.discord.WebHookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
@Aspect
@RequiredArgsConstructor
public class TransactionAspect {

    private static final String LOG_FORMAT = "A suspected N+1 problem has been discovered in \"{}()\". Entity: \"{}\", Count: {}";

    private final WebHookService webHookService;


    @Before("@within(org.springframework.stereotype.Service) && !@annotation(com.ittory.common.annotation.IgnoreNPlusOneLogging)")
    public void beforeTransactionStart() {
        LazyLoadingDetector.resetNPlusOneCount(); // 초기화
    }

    @After("@within(org.springframework.stereotype.Service) && !@annotation(com.ittory.common.annotation.IgnoreNPlusOneLogging)")
    public void afterTransactionEnd(JoinPoint joinPoint) {
        Map<String, Integer> nPlusOneCount = LazyLoadingDetector.getNPlusOneCount();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        for (String key : nPlusOneCount.keySet()) {
            log.warn(LOG_FORMAT,
                    className + "." + methodName,
                    key,
                    nPlusOneCount.get(key)
            );

            // WebHook은 잠시 비활성화
//            sendDiscordMessage(nPlusOneCount, className, methodName, key);
        }
    }

    private void sendDiscordMessage(Map<String, Integer> nPlusOneCount, String className, String methodName, String key) {
        String message = buildMessage(className + "." + methodName, key, nPlusOneCount.get(key));
        webHookService.sendNPlusOneOccurrence(message);
    }

    private String buildMessage(String methodPath, String key, Integer count) {
        String[] values = {methodPath, key, count.toString()};
        String message = LOG_FORMAT;
        for (String value : values) {
            message = message.replaceFirst("\\{}", value);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("```").append(message).append("```");
        return sb.toString();
    }
}
