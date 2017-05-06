package com.sdaacademy.kasperek.andrzej.apitasks.model;

/**
 * Created by RENT on 2017-04-29.
 */

public class Task {
    private boolean completed;
    private long id;
    private String value;

    public Task() {
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
