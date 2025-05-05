package com.ittory.socket.controller;

import com.ittory.common.annotation.CurrentMemberId;
import com.ittory.socket.dto.DeleteResponse;
import com.ittory.socket.dto.StartResponse;
import com.ittory.socket.service.LetterActionService;
import com.ittory.socket.service.LetterProcessService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
@RequiredArgsConstructor
public class LetterProcessController {

    private final SimpMessagingTemplate messagingTemplate;
    private final LetterProcessService letterProcessService;
    private final LetterActionService letterActionService;

    @MessageMapping("/letter/start/{letterId}")
    public void startMember(@DestinationVariable Long letterId) {
        log.info("Start letter {}", letterId);
        StartResponse response = letterProcessService.startLetter(letterId);
        String destination = "/topic/letter/" + letterId;
        messagingTemplate.convertAndSend(destination, response);
    }

    @MessageMapping("/letter/delete/{letterId}")
    public void deleteLetter(@CurrentMemberId Long memberId, @DestinationVariable Long letterId) {
        log.info("Delete letter {}", letterId);
        DeleteResponse response = letterActionService.deleteLetter(memberId, letterId);
        String destination = "/topic/letter/" + letterId;
        messagingTemplate.convertAndSend(destination, response);
    }

}
