package com.microservices.netflix.common.results;

public enum CustomStatusCodes {

    INITIAL_CODE("0000"),
    OBJECT_ALREADY_EXIST("4001"),
    ALL_FIELDS_REQUIRED("4002"),
    OBJECT_NOT_FOUND("4003");

    public final String value;

    CustomStatusCodes(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}

//Success için kod dönüyor mu?
//Dönüş tipi doğru mu?
//Kontrolleri controllerda yapıyorum doğru mu?
//Normal errorlar için kod gerekli mi?