package com.saf.messages;

import com.saf.core.Message;

public class TalkMessage implements Message {
    private String content;

    public TalkMessage() {}

    public TalkMessage(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
