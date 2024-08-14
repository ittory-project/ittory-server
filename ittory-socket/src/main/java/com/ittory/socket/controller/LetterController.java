package com.ittory.socket.controller;

import com.ittory.socket.config.handler.WebSocketSessionHandler;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LetterController {

    private final WebSocketSessionHandler webSocketSessionHandler;

    @GetMapping("/api/letter/member/current/{letterId}")
    public List<Long> getCurrentMember(@PathVariable Long letterId) {
        List<Long> sessions = webSocketSessionHandler.getSessions(letterId);
        return sessions;
    }
}
