package com.example.urltest.EventBus;

public class showTitleEvent {
    boolean success;

    public showTitleEvent(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
