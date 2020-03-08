package com.yjh.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class MyLtUtil {

    public static final String EN_TIME_FORMAT = "HH:mm:ss";
    public static final String CH_TIME_FORMAT = "HH时MM分dd秒";

    public static LocalTime now(){return LocalTime.now();}
    public static String getStr(){ return getStr(now()); }
    public static String getStr(LocalTime ldt){ return getStr(ldt,EN_TIME_FORMAT); }
    public static String getStr(LocalTime ldt,String format){ return ldt.format(DateTimeFormatter.ofPattern(format)); }

    public static LocalTime parse(String strLdt){
        return parse(strLdt,EN_TIME_FORMAT);
    }
    public static LocalTime parse(String strLdt,String format){
        return LocalTime.parse(strLdt,DateTimeFormatter.ofPattern(format));
    }

    public static LocalTime plus(Long num, MyLdtUnit unit){
        return plus(now(),num,unit);
    }
    public static String plusStr(Long num, MyLdtUnit unit){
        LocalTime ldt =  plus(now(),num,unit);
        return getStr(ldt);
    }
    public static LocalTime plus(LocalTime ldt, Long num, MyLdtUnit unit){
        switch (unit){
            case SECOND:
                ldt = ldt.plusSeconds(num);
                break;
            case MINUTE:
                ldt = ldt.plusMinutes(num);
                break;
            case HOUR:
                ldt = ldt.plusHours(num);
                break;
        }
        return ldt;
    }

    public static Integer compare(String mainStr,String viceStr){
        LocalTime mainLdt = parse(mainStr);
        LocalTime viceLdt = parse(viceStr);
        return compare(mainLdt,viceLdt);
    }
    public static Integer compare(LocalTime mainLdt,LocalTime viceLdt){
        if (viceLdt.isAfter(mainLdt)){
            return 1;
        }
        if (viceLdt.isBefore(mainLdt)){
            return -1;
        }
        return 0;
    }


 
}
