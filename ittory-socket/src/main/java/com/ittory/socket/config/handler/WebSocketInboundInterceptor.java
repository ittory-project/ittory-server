package com.ittory.socket.config.handler;

import static com.ittory.common.constant.TokenConstant.ACCESS_TOKEN_HEADER;

import com.ittory.common.jwt.AccessTokenInfo;
import com.ittory.common.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class WebSocketInboundInterceptor implements ChannelInterceptor {

    private final JwtProvider jwtProvider;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor headerAccessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (headerAccessor.getCommand() == StompCommand.CONNECT) {
            String accessToken = String.valueOf(headerAccessor.getNativeHeader(ACCESS_TOKEN_HEADER));
            if (accessToken != null) {
                AccessTokenInfo accessTokenInfo = jwtProvider.resolveToken(accessToken);
                SimpMessageHeaderAccessor wrap = SimpMessageHeaderAccessor.wrap(message);
                wrap.getSessionAttributes().put("member_id", accessTokenInfo.getMemberId());
            }
        }

        return ChannelInterceptor.super.preSend(message, channel);
    }

}
