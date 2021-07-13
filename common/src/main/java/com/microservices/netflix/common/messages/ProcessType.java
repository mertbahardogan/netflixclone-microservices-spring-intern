package com.microservices.netflix.common.messages;

public enum ProcessType {
    ADD(0),
    UPDATE(1),
    DELETE(2);

    public final int value;

    ProcessType(int value) {
        this.value = value;
    }
}
