package com.ittory.socket.common.exception;

import com.ittory.common.exception.GlobalException;
import com.ittory.infra.discord.WebHookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class WebSocketExceptionAdvice {

    private static final String LOG_FORMAT = "Error: {}, Class : {}, Message : {}";
    private static final String LOG_CODE_FORMAT = "Error: {}, Class : {}, Code : {}, Message : {}";

    private final WebHookService webHookService;

    @MessageExceptionHandler(GlobalException.class)
    public void handleWebSocketGlobalException(GlobalException exception) {
        log.error(LOG_CODE_FORMAT, "GlobalException", exception.getClass().getName(),
                exception.getErrorInfo().getCode(), exception.getMessage());
        StringBuilder sb = new StringBuilder();
        StringBuilder message = sb.append("======= Socket Error =======").append("\n")
                .append(exception.getClass().getName())
                .append(" : ")
                .append(exception.getErrorInfo().getCode())
                .append(" -> ")
                .append(exception.getMessage());
        webHookService.sendErrorLog(message.toString());
    }

    @MessageExceptionHandler(RuntimeException.class)
    public void handleWebSocketException(Exception exception) {
        log.error(LOG_FORMAT, "RuntimeException", exception.getClass().getName(), exception.getMessage());
        StringBuilder sb = new StringBuilder();
        StringBuilder message = sb.append("======= Socket Error =======").append("\n")
                .append(exception.getClass().getName())
                .append(" -> ")
                .append(exception.getMessage());
        webHookService.sendErrorLog(message.toString());
    }

}