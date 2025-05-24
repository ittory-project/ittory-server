package com.ittory.socket.utils;

import com.ittory.domain.participant.domain.Participant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class ConnectSessionStorage {

    private final Map<String, Participant> SESSIONID_KEY_MAP = new ConcurrentHashMap<>();
    private final Map<Long, String> MEMBERID_KEY_MAP = new ConcurrentHashMap<>();


    public void saveSessionId(String sessionId, Participant participant) {
        SESSIONID_KEY_MAP.put(sessionId, participant);
        MEMBERID_KEY_MAP.put(participant.getMember().getId(), sessionId);
    }

    public Participant findParticipantByMemberId(Long memberId) {
        if (!MEMBERID_KEY_MAP.containsKey(memberId)) {
            return null;
        }
        String sessionId = MEMBERID_KEY_MAP.get(memberId);

        if (!SESSIONID_KEY_MAP.containsKey(sessionId)) {
            return null;
        }
        return SESSIONID_KEY_MAP.get(sessionId);
    }

    public Participant findParticipantBySessionId(String sessionId) {
        if (!SESSIONID_KEY_MAP.containsKey(sessionId)) {
            return null;
        }
        return SESSIONID_KEY_MAP.get(sessionId);
    }

    public String findSessionIdByMemberId(Long memberId) {
        if (!MEMBERID_KEY_MAP.containsKey(memberId)) {
            return null;
        }
        return MEMBERID_KEY_MAP.get(memberId);
    }

    public Participant findMemberIdBySessionId(String sessionId) {
        if (!SESSIONID_KEY_MAP.containsKey(sessionId)) {
            return null;
        }
        return SESSIONID_KEY_MAP.get(sessionId);
    }

    public void removeBySessionId(String sessionId) {
        if (SESSIONID_KEY_MAP.containsKey(sessionId)) {
            Participant removedParticipant = SESSIONID_KEY_MAP.remove(sessionId);
            MEMBERID_KEY_MAP.remove(removedParticipant.getMember().getId());
        }
    }

    public void removeByMemberId(Long memberId) {
        if (MEMBERID_KEY_MAP.containsKey(memberId)) {
            String removedSessionId = MEMBERID_KEY_MAP.remove(memberId);
            SESSIONID_KEY_MAP.remove(removedSessionId);
        }
    }

}
