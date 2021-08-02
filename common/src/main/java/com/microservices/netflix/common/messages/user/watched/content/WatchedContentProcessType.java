package com.microservices.netflix.common.messages.user.watched.content;

public enum WatchedContentProcessType {
    ADD(0),
    UPDATE(1);

    public final int value;

    WatchedContentProcessType(int value) {
        this.value = value;
    }
}
