package tn.solixy.delivite.services;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketSessionHandler {
    private final Map<Long, WebSocketSession> sessions = new ConcurrentHashMap<>();

    public void addSession(Long chauffeurId, WebSocketSession session) {
        sessions.put(chauffeurId, session);
    }

    public void removeSession(Long chauffeurId) {
        sessions.remove(chauffeurId);
    }

    public void sendNotificationToChauffeur(Long chauffeurId, String message) {
        WebSocketSession session = sessions.get(chauffeurId);
        if (session != null && session.isOpen()) {
            try {
                session.sendMessage(new TextMessage(message));
            } catch (IOException e) {
                // Gestion des erreurs
            }
        }
    }
}
