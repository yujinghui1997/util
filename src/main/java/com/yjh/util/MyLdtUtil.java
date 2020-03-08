package com.yjh.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MyLdtUtil {

    public static final String EN_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String CH_DATE_TIME_FORMAT = "yyyy年MM月dd日 HH时mm分ss秒";

    public static LocalDateTime now(){return LocalDateTime.now();}
    public static String getStr(){ return getStr(now()); }
    public static String getStr(LocalDateTime ldt){ return getStr(ldt,EN_DATE_TIME_FORMAT); }
    public static String getStr(LocalDateTime ldt,String format){ return ldt.format(DateTimeFormatter.ofPattern(format)); }

    public static LocalDateTime parse(String strLdt){
        return parse(strLdt,EN_DATE_TIME_FORMAT);
    }
    public static LocalDateTime parse(String strLdt,String format){
        return LocalDateTime.parse(strLdt,DateTimeFormatter.ofPattern(format));
    }

    public static LocalDateTime plus(Long num, MyLdtUnit unit){
        return plus(now(),num,unit);
    }
    public static String plusStr(Long num, MyLdtUnit unit){
        LocalDateTime ldt =  plus(now(),num,unit);
        return getStr(ldt);
    }
    public static LocalDateTime plus(LocalDateTime ldt, Long num, MyLdtUnit unit){
       switch (unit){
           case SECOND:
               ldt = ldt.plusSeconds(num);
               break;
           case MINUTE:
               ldt = ldt.plusDays(num);
                break;
           case HOUR:
               ldt = ldt.plusHours(num);
               break;
           case DAY:
               ldt = ldt.plusDays(num);
               break;
           case WEEK:
               ldt = ldt.plusWeeks(num);
               break;
           case MONTH:
               ldt = ldt.plusMonths(num);
               break;
           case YEAR:
               ldt = ldt.plusYears(num);
               break;
       }
       return ldt;
    }

    public static Integer compare(String mainStr,String viceStr){
        LocalDateTime mainLdt = parse(mainStr);
        LocalDateTime viceLdt = parse(viceStr);
        return compare(mainLdt,viceLdt);
    }
    public static Integer compare(LocalDateTime mainLdt,LocalDateTime viceLdt){
        if (viceLdt.isEqual(mainLdt)){
            return 0;
        }
        if (viceLdt.isBefore(mainLdt)){
            return -1;
        }
        return 1;
    }

}
