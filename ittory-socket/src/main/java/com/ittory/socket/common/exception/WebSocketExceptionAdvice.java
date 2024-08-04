package com.ittory.socket.common.exception;

import com.ittory.common.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class WebSocketExceptionAdvice {

    private static final String LOG_FORMAT = "Error: {}, Class : {}, Message : {}";
    private static final String LOG_CODE_FORMAT = "Error: {}, Class : {}, Code : {}, Message : {}";

    private final SimpMessagingTemplate messagingTemplate;

    @MessageExceptionHandler(GlobalException.class)
    public void handleCustomException(GlobalException exception) {
        log.error(LOG_CODE_FORMAT, "GlobalException", exception.getClass().getName(),
                exception.getErrorInfo().getCode(), exception.getMessage());
    }

//    protected Message<byte[]> handleInternal(StompHeaderAccessor errorHeaderAccessor, byte[] errorPayload,
//                                             @Nullable Throwable cause,
//                                             @Nullable StompHeaderAccessor clientHeaderAccessor) {
//
//        return MessageBuilder.createMessage(errorPayload, errorHeaderAccessor.getMessageHeaders());
//    }

    @MessageExceptionHandler(RuntimeException.class)
    public void handleRuntimeException(Exception exception) {
        log.error(LOG_FORMAT, "RuntimeException", exception.getClass().getName(), exception.getMessage());
    }

}
