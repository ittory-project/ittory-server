package com.ittory.socket.config;

import com.ittory.socket.common.resolver.AuthMemberIdArgumentResolver;
import com.ittory.socket.config.handler.WebSocketErrorHandler;
import com.ittory.socket.config.handler.WebSocketInboundInterceptor;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@RequiredArgsConstructor
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final WebSocketInboundInterceptor webSocketInboundInterceptor;
    private final AuthMemberIdArgumentResolver authMemberIdArgumentResolver;
    private final WebSocketErrorHandler webSocketErrorHandler;

    private static final long HEARTBEAT_TIME = 30000L;
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic")
                .setHeartbeatValue(new long[]{HEARTBEAT_TIME, HEARTBEAT_TIME})
                .setTaskScheduler(this.customBrokerTaskScheduler());
        config.setApplicationDestinationPrefixes("/ws");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/connection")
                .setAllowedOriginPatterns("*"); // 런칭 시 설정 필요

        registry.setErrorHandler(webSocketErrorHandler);

    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(webSocketInboundInterceptor);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(authMemberIdArgumentResolver);
    }

    @Bean(name = "customBrokerTaskScheduler")
    public ThreadPoolTaskScheduler customBrokerTaskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(1);
        scheduler.setThreadNamePrefix("broker-heartbeat-");
        scheduler.initialize();
        return scheduler;
    }
}