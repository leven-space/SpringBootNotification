package com.fullstack.learn.ssedemo.stomp;

import java.security.Principal;

/**
 * @author leven.chen
 * @project sse-demo
 * @date 2019/7/22 12:00
 * @apiNote
 */
public class HttpSessionPrincipal implements Principal {

    private String username;

    public HttpSessionPrincipal(String username) {
        this.username = username;
    }

    @Override
    public String getName() {
        return  this.username;
    }
}
