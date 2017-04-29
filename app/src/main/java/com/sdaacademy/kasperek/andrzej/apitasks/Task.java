package com.sdaacademy.kasperek.andrzej.apitasks;

/**
 * Created by RENT on 2017-04-29.
 */

public class Task {
    private boolean compleated;
    private long id;
    private long user;
    private String value;

    public boolean isCompleated() {
        return compleated;
    }

    public void setCompleated(boolean compleated) {
        this.compleated = compleated;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUser() {
        return user;
    }

    public void setUser(long user) {
        this.user = user;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
