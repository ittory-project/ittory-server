package com.ittory.socket.service;

import com.ittory.domain.participant.domain.Participant;
import com.ittory.socket.utils.SessionUserStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ParticipantSessionService {

    private final SessionUserStore sessionUserStore;

    @Transactional
    public Participant exitUserBySession(String sessionId) {
        Participant sessionParticipant = sessionUserStore.getUserIdBySession(sessionId).orElse(null);
        sessionUserStore.removeSession(sessionId);
        return sessionParticipant;
    }

    @Transactional(readOnly = true)
    public Participant findUerBySession(String sessionId) {
        return sessionUserStore.getUserIdBySession(sessionId).orElse(null);
    }
}
