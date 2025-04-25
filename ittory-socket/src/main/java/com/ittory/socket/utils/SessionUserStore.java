package com.ittory.socket.utils;

import com.ittory.domain.participant.domain.Participant;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionUserStore {

    private final Map<String, Participant> sessionToUserMap = new ConcurrentHashMap<>();

    public void registerSession(String sessionId, Participant participant) {
        sessionToUserMap.put(sessionId, participant);
    }

    public Optional<Participant> getUserIdBySession(String sessionId) {
        return Optional.ofNullable(sessionToUserMap.get(sessionId));
    }

    public void removeSession(String sessionId) {
        sessionToUserMap.remove(sessionId);
    }
}
