package com.microservices.netflix.common.results;

public enum CustomStatusCodes {
    GENERAL_CATCH_ERROR(80001),
    ALL_FIELDS_REQUIRED(80002),
    OBJECT_NOT_FOUND(80003),
    OBJECT_ALREADY_EXIST(80004),
    OBJECT_HAS_SAME_VALUE(80005),
    DATA_NOT_LISTED(80006),
    DATA_NOT_FOUND(80007);

    public final int value;

    CustomStatusCodes(int value) {
        this.value = value;
    }

    public int getValue(){
        return value;
    }
}
