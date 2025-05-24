package com.ittory.socket.service;

import com.ittory.domain.participant.domain.Participant;
import com.ittory.socket.utils.ConnectSessionStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParticipantSessionService {

    private final ConnectSessionStorage connectSessionStorage;

    public Boolean existParticipantBySessionId(String sessionId) {
        return connectSessionStorage.findMemberIdBySessionId(sessionId) != null;
    }

    public Participant exitParticipantBySession(String sessionId) {
        Participant sessionParticipant = connectSessionStorage.findParticipantBySessionId(sessionId);
        connectSessionStorage.removeBySessionId(sessionId);
        return sessionParticipant;
    }

    public String findSessionIdByMemberId(Long memberId) {
        return connectSessionStorage.findSessionIdByMemberId(memberId);
    }

    public void changeSessionIdByMemberId(String sessionId, Long memberId) {
        Participant nowParticipant = connectSessionStorage.findParticipantByMemberId(memberId);
        connectSessionStorage.removeByMemberId(nowParticipant.getMember().getId());
        connectSessionStorage.saveSessionId(sessionId, nowParticipant);
    }

    public Participant findParticipantByMemberId(Long memberId) {
        return connectSessionStorage.findParticipantByMemberId(memberId);
    }

    public void removeAllSessions(List<Participant> allParticipants) {
        for (Participant participant : allParticipants) {
            connectSessionStorage.removeByMemberId(participant.getMember().getId());
        }
    }

}
