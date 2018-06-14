package com.jt.app.model;

import java.io.Serializable;

public class Msg implements Serializable {

    private String name;

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
}
