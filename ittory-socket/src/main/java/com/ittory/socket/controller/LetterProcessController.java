package com.ittory.socket.controller;

import com.ittory.socket.dto.EndResponse;
import com.ittory.socket.dto.StartResponse;
import com.ittory.socket.usecase.LetterEndUseCase;
import com.ittory.socket.usecase.LetterStartUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LetterProcessController {

    private final SimpMessagingTemplate messagingTemplate;

    private final LetterStartUseCase letterStartUseCase;
    private final LetterEndUseCase letterEndUseCase;

    @MessageMapping("/letter/start/{letterId}")
    public void startMember(@DestinationVariable Long letterId) {
        StartResponse response = letterStartUseCase.execute(letterId);
        String destination = "/topic/letter/" + letterId;
        messagingTemplate.convertAndSend(destination, response);
    }

    @MessageMapping("/letter/end/{letterId}")
    public void endMember(@DestinationVariable Long letterId) {
        EndResponse response = letterEndUseCase.execute(letterId);
        String destination = "/topic/letter/" + letterId;
        messagingTemplate.convertAndSend(destination, response);
    }

}
