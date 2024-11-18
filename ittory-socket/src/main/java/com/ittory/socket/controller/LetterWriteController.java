package com.ittory.socket.controller;

import com.ittory.common.annotation.CurrentMemberId;
import com.ittory.socket.dto.ElementRequest;
import com.ittory.socket.dto.ElementResponse;
import com.ittory.socket.usecase.LetterWriteUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class LetterWriteController {

    private final SimpMessagingTemplate messagingTemplate;

    private final LetterWriteUseCase letterWriteUseCase;

    @MessageMapping("/letter/{letterId}/elements")
    public void sendElement(@CurrentMemberId Long memberId, @DestinationVariable Long letterId,
                            ElementRequest request) {
        String destination = "/topic/letter/" + letterId;
        ElementResponse response = letterWriteUseCase.execute(memberId, letterId, request);
        messagingTemplate.convertAndSend(destination, response);
    }


}
