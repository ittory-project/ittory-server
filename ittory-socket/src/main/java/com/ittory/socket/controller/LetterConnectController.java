package com.ittory.socket.controller;

import com.ittory.common.annotation.CurrentMemberId;
import com.ittory.socket.dto.EnterResponse;
import com.ittory.socket.dto.ExitResponse;
import com.ittory.socket.usecase.LetterEnterUseCase;
import com.ittory.socket.usecase.LetterExitUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class LetterConnectController {

    private final SimpMessagingTemplate messagingTemplate;

    private final LetterEnterUseCase letterEnterUseCase;
    private final LetterExitUseCase letterExitUseCase;

    @MessageMapping("/letter/enter/{letterId}")
    public void enterMember(@CurrentMemberId Long memberId, @DestinationVariable Long letterId) {
        EnterResponse response = letterEnterUseCase.execute(memberId, letterId);
        String destination = "/topic/letter/" + letterId;
        messagingTemplate.convertAndSend(destination, response);
    }

    @MessageMapping("/letter/exit/{letterId}")
    public void exitMember(@CurrentMemberId Long memberId, @DestinationVariable Long letterId) {
        ExitResponse response = letterExitUseCase.execute(memberId, letterId);
        String destination = "/topic/letter/" + letterId;
        messagingTemplate.convertAndSend(destination, response);
    }

}
