package com.microservices.netflix.common.results;

public class SuccessResult extends Result {
    public SuccessResult() {
        super(true);
    }

    public SuccessResult(String message) {
        super(true, message);
    }

    public SuccessResult(String message,int customStatusCodes){
        super(true, message,customStatusCodes);
    }
}
