package com.jt.app.model;

import java.io.Serializable;

public class Msg implements Serializable {

    private String name;

    private Object content;

    public Msg(String name, Object content) {
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Msg(String name) {
        this.name = name;
    }

    public Msg() {
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }
}
