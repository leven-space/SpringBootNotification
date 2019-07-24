package com.fullstack.learn.ssedemo;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author leven.chen
 * @project sse-demo
 * @date 2019/7/15 11:15
 * @apiNote
 */
@RestController
public class DownloadController {

    /**
     * logger
     */
    private static final Logger log = getLogger(DownloadController.class);


    @Autowired
    private MockDownloadComponent downloadComponent;

    @GetMapping("/api/download/{type}")
    public String download(@PathVariable String type, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String sessionid = session.getId();
        log.info("sessionid=[{}]", sessionid);
        downloadComponent.mockDownload(type, sessionid);
        return "success";
    }


}
