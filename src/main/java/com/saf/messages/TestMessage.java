package com.saf.messages;

import com.saf.core.Message;

public class TestMessage implements Message {

    private String content;

    public TestMessage() {}

    public TestMessage(String content) {
        this.content = content;
    }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}
