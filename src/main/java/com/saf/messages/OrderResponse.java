package com.saf.messages;

import com.saf.core.Message;

public class OrderResponse implements Message {

    private String confirmation;

    public OrderResponse() {}

    public OrderResponse(String confirmation) {
        this.confirmation = confirmation;
    }

    public String getConfirmation() { return confirmation; }
    public void setConfirmation(String confirmation) { this.confirmation = confirmation; }
}
