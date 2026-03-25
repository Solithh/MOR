package me.alpha432.oyvey.event.impl.network;

import me.alpha432.oyvey.event.Event;

public class ChatEvent extends Event {
    private String message;

    public ChatEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
