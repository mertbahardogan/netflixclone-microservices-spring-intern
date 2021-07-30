package com.microservices.netflix.common.results;

public class ErrorResult extends Result {
    public ErrorResult() {
        super(false);
    }

    public ErrorResult(String message) {
        super(false, message);
    }

    public ErrorResult(String message,int customStatusCodes){
        super(false, message,customStatusCodes);
    }
}