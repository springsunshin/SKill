package com.skill.dto;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DateUtil {

    public Date parseStringToDate(String date){
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date1=format.parse(date);
            return date1;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
