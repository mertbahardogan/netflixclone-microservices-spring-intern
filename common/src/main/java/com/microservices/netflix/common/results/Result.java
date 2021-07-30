package com.microservices.netflix.common.results;

public class Result {
    private final boolean success;
    private String message;
    private int customStatusCodes;

    public Result(boolean success) {
        this.success = success;
    }

    public Result(boolean success, String message) {
        this(success);
        this.message = message;
    }

    public Result(boolean success, String message, int customStatusCodes) {
        this(success);
        this.message = message;
        this.customStatusCodes = customStatusCodes;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public String getMessage() {
        return this.message;
    }

    public int getCustomStatusCode() {
        return this.customStatusCodes;
    }
}
