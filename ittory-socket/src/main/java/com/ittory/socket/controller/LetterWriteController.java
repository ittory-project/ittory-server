package com.ittory.socket.controller;

import com.ittory.common.annotation.CurrentMemberId;
import com.ittory.socket.dto.ElementRequest;
import com.ittory.socket.dto.ElementResponse;
import com.ittory.socket.usecase.LetterWriteUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
@RequiredArgsConstructor
public class LetterWriteController {

    private final SimpMessagingTemplate messagingTemplate;

    private final LetterWriteUseCase letterWriteUseCase;

    @MessageMapping("/letter/{letterId}/elements")
    public void sendElement(@CurrentMemberId Long memberId, @DestinationVariable Long letterId,
                            ElementRequest request) {
        log.info("Write member {}, in letter {} => {}", memberId, letterId, request.getContent());
        String destination = "/topic/letter/" + letterId;
        ElementResponse response = letterWriteUseCase.execute(memberId, letterId, request);
        messagingTemplate.convertAndSend(destination, response);
    }


}
