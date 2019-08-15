package com.example.booking.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Helper {

    public static String getCurrentTime() {
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        DateFormat dateFormat = new SimpleDateFormat("MM:dd-HH:mm:ss");
        return dateFormat.format(date);
    }

    private Helper() {
    }
}