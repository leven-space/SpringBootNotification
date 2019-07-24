package com.fullstack.learn.ssedemo.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

/**
 * @author leven.chen
 * @project sse-demo
 * @date 2019/7/16 11:39
 * @apiNote
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    /**
     * logger
     */
    private static final Logger log = LoggerFactory.getLogger(WebSocketConfig.class);


    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        WebSocketNotificationHandler notificationHandler = new WebSocketNotificationHandler();

        registry.addHandler(notificationHandler, "/ws-notification")
                .addInterceptors(new HttpSessionHandshakeInterceptor())
                .withSockJS();
    }


}
