package com.ittory.socket.config.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class WebSocketSessionHandler {

    private final Map<Long, List<Long>> sessions = new ConcurrentHashMap<>();

    public void putSession(Long memberId, Long letterId) {
        if (!sessions.containsKey(letterId)) {
            sessions.put(letterId, new ArrayList<>());
        }
        sessions.get(letterId).add(memberId);
    }

    public void removeSession(Long memberId, Long letterId) {
        List<Long> letterSession = sessions.get(letterId);
        letterSession.remove(memberId);
        if (letterSession.size() == 0) {
            sessions.remove(letterId);
        }
    }

    public List<Long> getSessions(Long letterId) {
        return sessions.get(letterId);
    }


}
