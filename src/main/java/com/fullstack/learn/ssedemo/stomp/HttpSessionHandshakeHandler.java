package com.fullstack.learn.ssedemo.stomp;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.security.Principal;
import java.util.Map;

/**
 * @author leven.chen
 * @project sse-demo
 * @date 2019/7/16 19:34
 * @apiNote
 */
public class HttpSessionHandshakeHandler extends DefaultHandshakeHandler {

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        String sessionId = (String) attributes.get(HttpSessionHandshakeInterceptor.HTTP_SESSION_ID_ATTR_NAME);
        return new HttpSessionPrincipal(sessionId);

    }
}
