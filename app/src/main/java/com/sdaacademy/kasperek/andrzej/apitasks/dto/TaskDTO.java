package com.sdaacademy.kasperek.andrzej.apitasks.dto;

/**
 * Created by RENT on 2017-04-29.
 */

public class TaskDTO {
    private boolean completed;
    private long id;
    private long user;
    private String value;

    public TaskDTO() {
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public long getUser() {
        return user;
    }

    public void setUser(long user) {
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }


}
