package com.jt.app.thread;

public enum ThreadEvents {

    award("openLottery"),closeAward("closeLottery"),report("reportTask"),timerTask("timerTask"),fortySecdsTask("fortySecdsTask");

    private String eventName;

    ThreadEvents(String eventName){
        this.eventName = eventName;
    }

    public String getEventName(){
        return eventName;
    }
}
