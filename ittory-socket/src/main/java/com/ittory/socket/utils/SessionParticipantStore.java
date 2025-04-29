package com.ittory.socket.utils;

import com.ittory.domain.participant.domain.Participant;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionParticipantStore {

    private final Map<String, Participant> sessionToParticipantMap = new ConcurrentHashMap<>();
    private final Map<Long, String> memberIdToSessionMap = new ConcurrentHashMap<>();

    public void registerSession(String sessionId, Participant participant) {
        sessionToParticipantMap.put(sessionId, participant);
        memberIdToSessionMap.put(participant.getMember().getId(), sessionId);
    }

    public Optional<Participant> getParticipantBySessionId(String sessionId) {
        return Optional.ofNullable(sessionToParticipantMap.get(sessionId));
    }

    public void removeSession(String sessionId) {
        Participant removedParticipant = sessionToParticipantMap.remove(sessionId);
        memberIdToSessionMap.remove(removedParticipant.getId());
    }

    public Optional<Participant> getParticipantByMemberId(Long memberId) {
        String sessionId = memberIdToSessionMap.get(memberId);
        if (sessionId == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(sessionToParticipantMap.get(sessionId));
    }

    /**
     * 1. memberId로 old 세션아이디 찾기
     * 2. old 세션 아이디로 Participant 찾기
     * 3. 각 map 업데이트
     */
    public void changeSessionIdByMemberId(String sessionId, Long memberId) {
        String oldSessionId = memberIdToSessionMap.remove(memberId);
        Participant participant = sessionToParticipantMap.remove(oldSessionId);
        sessionToParticipantMap.put(sessionId, participant);
        memberIdToSessionMap.put(memberId, sessionId);
    }

    public Optional<String> getSessionIdByMemberId(Long memberId) {
        return Optional.ofNullable(memberIdToSessionMap.get(memberId));
    }
}
