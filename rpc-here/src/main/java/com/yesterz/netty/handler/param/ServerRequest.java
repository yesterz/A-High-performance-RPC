package com.yesterz.netty.handler.param;

public class ServerRequest {

    private Long id;
    private Object content;
    // command应该是某个类的某个方法，要知道调用的是哪个。
    private String command;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }
}
