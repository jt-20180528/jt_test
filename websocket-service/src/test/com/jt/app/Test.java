package com.jt.app;

import com.jt.app.util.TimeUtil;

import java.util.Calendar;
import java.util.concurrent.CancellationException;

public class Test {

    public static void main(String[] args){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE,2);
        System.out.print(TimeUtil.ymdHms2str(calendar.getTime()));;
    }
}
