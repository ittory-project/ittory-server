package com.ittory.socket.common.resolver;

import com.ittory.domain.member.domain.Member;
import com.ittory.domain.member.service.MemberDomainService;
import com.ittory.socket.annotation.MessageMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthMemberIdArgumentResolver implements HandlerMethodArgumentResolver {

    private final MemberDomainService memberDomainService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(MessageMember.class) &&
                parameter.getParameterType().equals(Member.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, Message<?> message) {
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.wrap(message);
        Long memberId = Long.parseLong((String) headerAccessor.getSessionAttributes().get("member_id"));
        if (memberId == null) {
            System.out.println("error");
//            throw new WebSocketMessageSendingException("No member_id found in session attributes");
        }

        return memberDomainService.findMemberById(memberId);
    }

}
