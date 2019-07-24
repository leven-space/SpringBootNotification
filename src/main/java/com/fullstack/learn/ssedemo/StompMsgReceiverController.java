package com.fullstack.learn.ssedemo;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author leven.chen
 * @project sse-demo
 * @date 2019/7/22 10:30
 * @apiNote
 */
@Controller
public class StompMsgReceiverController {

    /**
     * logger
     */
    private static final Logger log = getLogger(StompMsgReceiverController.class);

    @Autowired
    private SimpMessagingTemplate messagingTemplate;


    @MessageMapping("/public-msg")
    public void publicMessage(@Payload String payload) {
        log.info("接受消息:{}", payload);
        messagingTemplate.convertAndSend("/topic/download-notification", "已接受到");
    }
}
