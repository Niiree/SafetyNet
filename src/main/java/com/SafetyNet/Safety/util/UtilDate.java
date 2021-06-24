package com.SafetyNet.Safety.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UtilDate {


    public boolean isAdult(Date date){
        Date dateNow = new Date(System.currentTimeMillis());
        SimpleDateFormat simpleDateNow = new SimpleDateFormat("dd/MM/yyyy");
        long between = dateNow.getTime() - date.getTime();

        if (between/(1000*60*60*24) > 6570 ){
            return true;
        }
        return false;
    }
}
