package com.fullstack.learn.ssedemo;

import com.fullstack.learn.ssedemo.sse.SseNotificationController;
import com.fullstack.learn.ssedemo.websocket.WebSocketNotificationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author leven.chen
 * @project sse-demo
 * @date 2019/7/15 11:57
 * @apiNote
 */
@Component
public class MockDownloadComponent {


    /**
     * logger
     */
    private static final Logger log = LoggerFactory.getLogger(DownloadController.class);

    @Autowired
    private SimpMessagingTemplate messagingTemplate;


    @Async
    public void mockDownload(String type, String sessionid) {
        for (int i = 0; i < 100; i++) {
            try {
                // time required to  download
                TimeUnit.MILLISECONDS.sleep(100);
                int percent = i + 1;
                // {"username":"abc","percent":1}
                String content = String.format("{\"username\":\"%s\",\"percent\":%d}", sessionid, percent);
                log.info("username={}'s file has been finished [{}]% ", sessionid, percent);

                switch (type) {
                    case "sse":
                        SseNotificationController.usesSsePush(sessionid, content);
                        break;
                    case "ws":
                        WebSocketNotificationHandler.usesWSPush(sessionid, content);
                        break;
                    case "stomp":
                        this.usesStompPush(sessionid, content);
                        break;
                    default:
                        throw new UnsupportedOperationException("");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void usesStompPush(String sessionid, String content) {
        String destination = "/queue/download-notification";
        messagingTemplate.convertAndSendToUser(sessionid, destination, content);
    }


}
