package com.ittory.socket.controller;

import com.ittory.common.annotation.CurrentMemberId;
import com.ittory.socket.dto.ElementRequest;
import com.ittory.socket.dto.SimpleResponse;
import com.ittory.socket.dto.SubmitResponse;
import com.ittory.socket.enums.ActionType;
import com.ittory.socket.service.LetterProcessService;
import com.ittory.socket.service.LetterWriteService;
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

    private final LetterWriteService letterWriteService;
    private final LetterProcessService letterProcessService;

    @MessageMapping("/letter/{letterId}/elements")
    public void sendElement(@CurrentMemberId Long memberId, @DestinationVariable Long letterId,
                            ElementRequest request) {
        log.info("Write member {}, in letter {} => {}", memberId, letterId, request.getContent());
        String destination = "/topic/letter/" + letterId;
        SubmitResponse response = letterWriteService.writeElement(memberId, letterId, request);
        messagingTemplate.convertAndSend(destination, response);

        if (response.getUpcomingElement() == null) {
            log.info("Finish Letter {}", letterId);
            letterProcessService.finishLetter(letterId);
            String finishDestination = "/topic/letter/" + letterId;
            messagingTemplate.convertAndSend(finishDestination, SimpleResponse.from(ActionType.FINISH));
        }
    }


}
