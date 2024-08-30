package tn.solixy.delivite.Config;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

public class NotificationHandler extends TextWebSocketHandler {
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Logique pour gérer les messages entrants
    }

    public void sendNotification(WebSocketSession session, String message) throws IOException {
        session.sendMessage(new TextMessage(message));
    }
}
