package com.qfleaf.cosmosimserver.contact.interfaces.ws.cache;

import jakarta.websocket.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@RequiredArgsConstructor
public class ContactNotificationSessionCache {
    private static final Map<Long, Session> sessionCache = new ConcurrentHashMap<>();

    public void registerSession(Long uid, Session session) {
        sessionCache.put(uid, session);
    }

    public Session getSession(Long uid) {
        return sessionCache.get(uid);
    }

    public void removeSession(Long uid) {
        sessionCache.remove(uid);
    }
}
