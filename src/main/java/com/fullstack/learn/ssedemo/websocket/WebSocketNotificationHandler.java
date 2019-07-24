package com.fullstack.learn.ssedemo.websocket;

import org.slf4j.Logger;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author leven.chen
 * @project sse-demo
 * @date 2019/7/16 11:36
 * @apiNote
 */
public class WebSocketNotificationHandler extends TextWebSocketHandler {

    /**
     * logger
     */
    private static final Logger log = getLogger(WebSocketNotificationHandler.class);


    public static final Map<String, WebSocketSession> SESSIONS = new ConcurrentHashMap<>();


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String httpSessionId = (String) session.getAttributes().get(HttpSessionHandshakeInterceptor.HTTP_SESSION_ID_ATTR_NAME);
        SESSIONS.put(httpSessionId, session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info("handleTextMessage={}", message.getPayload());
    }

    public static void usesWSPush(String sessionid, String content) {
        WebSocketSession wssession = WebSocketNotificationHandler.SESSIONS.get(sessionid);
        if (Objects.nonNull(wssession)) {
            TextMessage textMessage = new TextMessage(content);
            try {
                wssession.sendMessage(textMessage);
            } catch (IOException | IllegalStateException e) {
                WebSocketNotificationHandler.SESSIONS.remove(sessionid);
            }
        }
    }
}
