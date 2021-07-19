package com.microservices.netflix.common.messages.user;

public enum FavouriteProcessType {
    ADD_TO_FAV(0),
    DELETE_FROM_FAV(1);

    public final int value;

    FavouriteProcessType(int value) {
        this.value = value;
    }
}
