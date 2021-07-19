package com.microservices.netflix.common.messages.film;

public enum FilmProcessType {
    ADD(0),
    UPDATE(1),
    DELETE(2);

    public final int value;

    FilmProcessType(int value) {
        this.value = value;
    }
}
