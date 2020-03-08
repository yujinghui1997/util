package com.yjh.util;

import org.springframework.cache.annotation.EnableCaching;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MyLdtUtil {

    public static final String EN_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String CH_DATE_TIME_FORMAT = "yyyy年MM月dd日 HH时mm分ss秒";

    public static LocalDateTime now(){return LocalDateTime.now();}
    public static String getStr(){ return getStr(now()); }
    public static String getStr(String format){
        return getStr(now(),format);
    }
    public static String getStr(LocalDateTime ldt){ return getStr(ldt,EN_DATE_TIME_FORMAT); }
    public static String getStr(LocalDateTime ldt,String format){ return ldt.format(DateTimeFormatter.ofPattern(format)); }

    public static LocalDateTime parse(String strLdt){
        return parse(strLdt,EN_DATE_TIME_FORMAT);
    }
    public static LocalDateTime parse(String strLdt,String format){
        return LocalDateTime.parse(strLdt,DateTimeFormatter.ofPattern(format));
    }

    public static LocalDateTime plus(String ldtStr,Long num, MyLdtUnit unit){
        return plus(ldtStr,EN_DATE_TIME_FORMAT,num,unit);
    }
    public static LocalDateTime plus(String ldtStr,String format,Long num, MyLdtUnit unit){
        LocalDateTime ldt =  parse(ldtStr,format);
        return plus(ldt,num,unit);
    }
    public static LocalDateTime plus(Long num, MyLdtUnit unit){
        return plus(now(),num,unit);
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


    public static String plusStr(String ldtStr,Long num, MyLdtUnit unit){
        return plusStr(ldtStr,EN_DATE_TIME_FORMAT,num,unit);
    }
    public static String plusStr(String ldtStr,String format,Long num, MyLdtUnit unit){
        LocalDateTime ldt =  plus(ldtStr,format,num,unit);
        return getStr(ldt,format);
    }
    public static String plusStr(Long num, MyLdtUnit unit){
        LocalDateTime ldt =  plus(now(),num,unit);
        return getStr(ldt);
    }
    public static String plusStr(LocalDateTime ldt,Long num,MyLdtUnit unit){
       return plusStr(ldt,EN_DATE_TIME_FORMAT,num,unit);
    }
    public static String plusStr(LocalDateTime ldt,String fromat,Long num,MyLdtUnit unit){
        ldt = plus(ldt,num,unit);
        return getStr(ldt,fromat);
    }

    public static Integer compare(String mainStr,String viceStr){
        return compare(mainStr,viceStr, EN_DATE_TIME_FORMAT);
    }
    public static Integer compare(String mainStr,String viceStr,String bothFormat){
       return compare(mainStr,bothFormat,viceStr,bothFormat);
    }
    public static Integer compare(String mainStr,String mainFormat,String viceStr,String viceFormat){
        LocalDateTime mainLdt = parse(mainStr,mainFormat);
        LocalDateTime viceLdt = parse(viceStr,viceFormat);
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

    public static Boolean isAm(String ldtStr){
      return isAm(ldtStr,EN_DATE_TIME_FORMAT);
    }
    public static Boolean isAm(String ldtStr,String format){
        LocalDateTime ldt = parse(ldtStr,format);
       return isAm(ldt);
    }
    public static Boolean isAm(LocalDateTime ldt){
        int hour =  ldt.getHour();
        return hour <= 12;
    }

    public static Boolean isPm(String ldtStr){
        return isAm(ldtStr,EN_DATE_TIME_FORMAT);
    }
    public static Boolean isPm(String ldtStr,String format){
        LocalDateTime ldt = parse(ldtStr,format);
        return isPm(ldt);
    }
    public static Boolean isPm(LocalDateTime ldt){
        int hour =  ldt.getHour();
        return hour > 12;
    }

    public static Boolean isRun(String ldtStr){
        return isRun(ldtStr,EN_DATE_TIME_FORMAT);
    }
    public static Boolean isRun(String ldtStr, String format){
       LocalDateTime ldt = parse(ldtStr,format);
       return isRun(ldt);
    }
    public static Boolean isRun(LocalDateTime ldt){
        return ldt.getYear() % 4 == 0;
    }


}
