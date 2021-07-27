package com.microservices.netflix.common.messages.user.rate;

public enum RateProcessType {
    ADD(0),
    UPDATE(1),
    DELETE(2);

    public final int value;

    RateProcessType(int value) {
        this.value = value;
    }
}
