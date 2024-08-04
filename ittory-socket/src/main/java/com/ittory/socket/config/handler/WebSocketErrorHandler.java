package com.ittory.socket.config.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ittory.common.exception.ErrorInfo;
import com.ittory.common.exception.ErrorResponse;
import com.ittory.common.exception.ErrorStatus;
import com.ittory.common.jwt.exception.JwtErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.StompSubProtocolErrorHandler;

@Component
@Slf4j
public class WebSocketErrorHandler extends StompSubProtocolErrorHandler {

    private static final String LOG_FORMAT = "Error: {}, Class : {}, Message : {}";
    private static final String LOG_CODE_FORMAT = "Error: {}, Class : {}, Code : {}, Message : {}";

    @Override
    public Message<byte[]> handleClientMessageProcessingError(Message<byte[]> clientMessage, Throwable ex) {

        if (ex instanceof MessageDeliveryException) {
            log.error(LOG_FORMAT, "GlobalException", ex.getClass().getSimpleName(), ex.getMessage());

            return handleConnectionException(clientMessage, ex);
        }

        return super.handleClientMessageProcessingError(clientMessage, ex);
    }

    private Message<byte[]> handleConnectionException(Message<byte[]> clientMessage, Throwable ex) {

        JwtErrorCode errorCode = JwtErrorCode.UNAUTHORIZED;
        ErrorInfo<?> errorInfo = new ErrorInfo<>(errorCode.getCode(), errorCode.getMessage(), null);

        return prepareErrorMessage(clientMessage, errorInfo, String.valueOf(errorCode));

    }

    private Message<byte[]> prepareErrorMessage(Message<byte[]> clientMessage, ErrorInfo<?> apiError,
                                                String errorCode) {

        ObjectMapper objectMapper = new ObjectMapper();
        ErrorResponse<?> errorResponse = ErrorResponse.of(ErrorStatus.BAD_REQUEST, apiError);
        try {
            byte[] errorResponseBytes = objectMapper.writeValueAsBytes(errorResponse);
            StompHeaderAccessor accessor = StompHeaderAccessor.create(StompCommand.ERROR);
            accessor.setMessage(errorCode);
            accessor.setLeaveMutable(true);

            return MessageBuilder.createMessage(errorResponseBytes, accessor.getMessageHeaders());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return clientMessage;
    }

}
