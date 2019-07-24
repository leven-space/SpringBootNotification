package com.fullstack.learn.ssedemo.sse;

import com.fullstack.learn.ssedemo.NotificationDemoApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author leven.chen
 * @project sse-demo
 * @date 2019/7/15 11:04
 * @apiNote
 */
@RestController
public class SseNotificationController {

    private static final Logger log = LoggerFactory.getLogger(NotificationDemoApplication.class);

    public static final Map<String, SseEmitter> SSE_HOLDER = new ConcurrentHashMap<>();


    @GetMapping("/api/sse-notification")
    public SseEmitter files(HttpServletRequest request) {
        long millis = TimeUnit.SECONDS.toMillis(60);
        SseEmitter sseEmitter = new SseEmitter(millis);

        HttpSession session = request.getSession();
        String sessionid = session.getId();
        log.info("sessionid=[{}]", sessionid);

        SSE_HOLDER.put(sessionid, sseEmitter);
        return sseEmitter;
    }

    /**
     * 通过sessionId获取对应的客户端进行推送消息
     */
    public static void usesSsePush(String sessionid, String content) {
        SseEmitter emitter = SseNotificationController.SSE_HOLDER.get(sessionid);
        if (Objects.nonNull(emitter)) {
            try {
                emitter.send(content);
            } catch (IOException | IllegalStateException e) {
                log.warn("sse send error", e);
                SseNotificationController.SSE_HOLDER.remove(sessionid);
            }
        }
    }
}
