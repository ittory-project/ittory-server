package com.ittory.socket.controller;

import com.ittory.common.annotation.CurrentMemberId;
import com.ittory.socket.dto.EnterResponse;
import com.ittory.socket.dto.ExitResponse;
import com.ittory.socket.service.LetterActionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
@RequiredArgsConstructor
public class LetterConnectController {

    private final SimpMessagingTemplate messagingTemplate;
    private final LetterActionService letterActionService;

    @MessageMapping("/letter/enter/{letterId}")
    public void enterMember(@CurrentMemberId Long memberId, @DestinationVariable Long letterId, SimpMessageHeaderAccessor headerAccessor) {
        String sessionId = headerAccessor.getSessionId();
        log.info("member {}, enter to letter {} with SessionId: {}", memberId, letterId, sessionId);
        EnterResponse response = letterActionService.enterToLetter(memberId, letterId, sessionId);
        String destination = "/topic/letter/" + letterId;
        messagingTemplate.convertAndSend(destination, response);
    }

    @MessageMapping("/letter/exit/{letterId}")
    public void exitMember(@CurrentMemberId Long memberId, @DestinationVariable Long letterId) {
        log.info("member {}, exit from letter {}", memberId, letterId);
        ExitResponse response = letterActionService.exitFromLetter(memberId, letterId);
        String destination = "/topic/letter/" + letterId;
        messagingTemplate.convertAndSend(destination, response);
    }

}
