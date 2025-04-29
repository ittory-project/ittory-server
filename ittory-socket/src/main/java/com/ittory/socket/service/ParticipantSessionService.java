package com.ittory.socket.service;

import com.ittory.domain.participant.domain.Participant;
import com.ittory.socket.utils.SessionParticipantStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParticipantSessionService {

    private final SessionParticipantStore sessionParticipantStore;

    public Participant exitParticipantBySession(String sessionId) {
        Participant sessionParticipant = sessionParticipantStore.getParticipantBySessionId(sessionId).orElse(null);
        sessionParticipantStore.removeParticipantBySession(sessionId);
        return sessionParticipant;
    }

    public Participant findParticipantByMemberId(Long memberId) {
        return sessionParticipantStore.getParticipantByMemberId(memberId).orElse(null);
    }

    public void changeSessionIdByMemberId(String sessionId, Long memberId) {
        sessionParticipantStore.changeSessionIdByMemberId(sessionId, memberId);
    }

    public String findSessionIdByMemberId(Long memberId) {
        return sessionParticipantStore.getSessionIdByMemberId(memberId).orElse(null);
    }

    public Boolean existParticipantBySessionId(String sessionId) {
        return sessionParticipantStore.getParticipantBySessionId(sessionId).isPresent();
    }
}
