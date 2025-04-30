package com.ittory.socket.common.exception;

import com.ittory.common.exception.GlobalException;
import com.ittory.infra.discord.WebHookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class WebSocketExceptionAdvice {

    private static final String LOG_FORMAT = "Error: {}, Class : {}, Message : {}";
    private static final String LOG_CODE_FORMAT = "Error: {}, Class : {}, Code : {}, Message : {}";

    private final WebHookService webHookService;

    @MessageExceptionHandler(GlobalException.class)
    public void handleWebSocketGlobalException(GlobalException exception, Message<?> message) {
        log.error(LOG_CODE_FORMAT, "GlobalException", exception.getClass().getName(),
                exception.getErrorInfo().getCode(), exception.getMessage());

        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        String destination = accessor.getDestination();
        String messageBody = null;
        Object payload = message.getPayload();
        if (payload instanceof byte[]) {
            messageBody = new String((byte[]) payload, StandardCharsets.UTF_8);
        }
        Object memberId = accessor.getSessionAttributes().get("member_id");

        StringBuilder sb = new StringBuilder();
        StringBuilder webHookMessage = sb.append("======= Socket Error =======").append("\n")
                .append(exception.getClass().getName())
                .append(" : ")
                .append(exception.getErrorInfo().getCode())
                .append(" -> ")
                .append(exception.getMessage()).append("\n\n")
                .append("== Stack Trace ==").append("\n")
                .append(Arrays.toString(exception.getStackTrace())).append("\n\n")
                .append("== Request Trace ==").append("\n")
                .append("Destination: ").append(destination).append("\n")
                .append("Message Content: ").append(messageBody).append("\n")
                .append("MemberId: ").append(memberId.toString()).append("\n");

        webHookService.sendErrorLog(webHookMessage.toString());
    }

    @MessageExceptionHandler(RuntimeException.class)
    public void handleWebSocketException(Exception exception, Message<?> message) {
        log.error(LOG_FORMAT, "RuntimeException", exception.getClass().getName(), exception.getMessage());

        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        String destination = accessor.getDestination();
        String messageBody = null;
        Object payload = message.getPayload();
        if (payload instanceof byte[]) {
            messageBody = new String((byte[]) payload, StandardCharsets.UTF_8);
        }
        Object memberId = accessor.getSessionAttributes().get("member_id");

        StringBuilder sb = new StringBuilder();
        StringBuilder webHookMessage = sb.append("======= Socket Error =======").append("\n")
                .append(exception.getClass().getName())
                .append(" -> ")
                .append(exception.getMessage()).append("\n\n")
                .append("== Stack Trace ==").append("\n")
                .append(Arrays.toString(exception.getStackTrace())).append("\n\n")
                .append("== Request Trace ==").append("\n")
                .append("Destination: ").append(destination).append("\n")
                .append("Message Content: ").append(messageBody).append("\n")
                .append("MemberId: ").append(memberId.toString()).append("\n");

        webHookService.sendErrorLog(webHookMessage.toString());
    }

}