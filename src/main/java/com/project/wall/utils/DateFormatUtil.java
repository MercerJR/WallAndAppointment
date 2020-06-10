package com.project.wall.utils;

import com.project.wall.data.Message;
import com.project.wall.exception.CustomException;
import com.project.wall.exception.CustomExceptionType;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author MercerJR
 * @Data 2020/5/13 10:57
 */
@Component
public class DateFormatUtil {
    public Long getDateStr(Integer year,Integer month,Integer day){
        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String m = month < 10 ? "0" + month : String.valueOf(month);
        String d = day < 10 ? "0" + day : String.valueOf(day);
        String time = String.valueOf(year) + "-" + m + "-" + d + " 00:00:00";
        Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            throw new CustomException(CustomExceptionType.SYSTEM_ERROR, Message.CONTACT_ADMIN);
        }
        return date.getTime();
//        "2020-05-10 16:39:00"
    }

    public Long getMilesByDate(String date){
//        "2001-03-15 15-37-05";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        long time = 0;
        try {
            time = simpleDateFormat.parse(date).getTime();
        } catch (ParseException e) {
            throw new CustomException(CustomExceptionType.SYSTEM_ERROR, Message.CONTACT_ADMIN);
        }
        System.out.println(time);
        return time;
    }

    public String getDateByMiles(Long miles){
        Long time = miles ;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        Date date = new Date();
        date.setTime(time);
        System.out.println(simpleDateFormat.format(date));
        return simpleDateFormat.format(date);
    }

    public String getWallLikeKeyByMiles(Long time){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String m = month < 10 ? "0" + month : String.valueOf(month);
        String d = day < 10 ? "0" + day : String.valueOf(day);
        return year + m + ":" + d;
    }

    public String getWallLikeKeyByDate(int year,int month,int day){
        String m = month < 10 ? "0" + month : String.valueOf(month);
        String d = day < 10 ? "0" + day : String.valueOf(day);
        return year + m + ":" + d;
    }
}
