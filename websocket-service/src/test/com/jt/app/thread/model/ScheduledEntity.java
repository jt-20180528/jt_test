package com.jt.app.thread.model;

import java.util.Date;

public class ScheduledEntity extends ThreadEntity{

    private Date startTime;

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Override
    public String toString() {
        return "ScheduledEntity{" +
                "name='" + super.getName() + '\'' +
                ", address='" + super.getAddress() + '\'' +
                ", telephone='" + super.getTelephone() + '\'' +
                "startTime=" + startTime +
                '}';
    }
}
