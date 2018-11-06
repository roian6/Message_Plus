package com.example.david0926.messageplus;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GetTimeDate {

    public String getTime(){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm aa");
        String timeNow = simpleDateFormat.format(date);
        return timeNow;
    }

    public String getDate(){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String dateNow = simpleDateFormat.format(date);
        return dateNow;
    }

}
