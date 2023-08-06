package com.yesterz.netty.client;

import java.util.concurrent.atomic.AtomicLong;

public class ClientRequest {

    private final long id;
    private Object content;
    private final AtomicLong aid = new AtomicLong(1);
    private String command;

    public ClientRequest() {
        id = aid.incrementAndGet();
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public long getId() {
        return id;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public AtomicLong getAid() {
        return aid;
    }
}
