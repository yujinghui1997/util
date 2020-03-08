package com.yjh.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class MyLtUtil {

    public static final String EN_TIME_FORMAT = "HH:mm:ss";
    public static final String CH_TIME_FORMAT = "HH时MM分dd秒";

    public static LocalTime now(){return LocalTime.now();}
    public static String getStr(){ return getStr(now()); }
    public static String getStr(String format){
        return getStr(now(),format); }
    public static String getStr(LocalTime ldt){ return getStr(ldt,EN_TIME_FORMAT); }
    public static String getStr(LocalTime ldt,String format){ return ldt.format(DateTimeFormatter.ofPattern(format)); }

    public static LocalTime parse(String strLdt){
        return parse(strLdt,EN_TIME_FORMAT);
    }
    public static LocalTime parse(String strLdt,String format){
        return LocalTime.parse(strLdt,DateTimeFormatter.ofPattern(format));
    }

    public static LocalTime plus(String ltStr,Long num,MyLdtUnit unit){
        return plus(ltStr,EN_TIME_FORMAT,num,unit);
    }
    public static LocalTime plus(String ltStr,String format,Long num,MyLdtUnit unit){
        LocalTime lt = parse(ltStr,format);
        return plus(lt,num,unit);
    }
    public static LocalTime plus(Long num, MyLdtUnit unit){
        return plus(now(),num,unit);
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
    public static String plusStr(Long num, MyLdtUnit unit){
        LocalTime ldt =  plus(now(),num,unit);
        return getStr(ldt);
    }
    public static String plusStr(String ltStr,Long num,MyLdtUnit unit){
        return plusStr(ltStr,EN_TIME_FORMAT,num,unit);
    }
    public static String plusStr(String ltStr,String format,Long num,MyLdtUnit unit){
        LocalTime lt = plus(ltStr,format,num,unit);
        return getStr(lt,format);
    }
    public static String plusStr(LocalTime lt,Long num,MyLdtUnit unit){
        return plusStr(lt,EN_TIME_FORMAT,num,unit);
    }
    public static String plusStr(LocalTime lt,String format,Long num,MyLdtUnit unit){
        lt = plus(lt,num,unit);
        return getStr(lt,format);
    }


    public static Integer compare(String mainStr,String viceStr){
        return compare(mainStr,viceStr,EN_TIME_FORMAT);
    }
    public static Integer compare(String mainStr,String viceStr,String bothFormat){
      return compare(mainStr,bothFormat,viceStr,bothFormat);
    }
    public static Integer compare(String mainStr,String mainFormat,String viceStr,String viceFormat){
        LocalTime mainLdt = parse(mainStr,mainFormat);
        LocalTime viceLdt = parse(viceStr,viceFormat);
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


    public static Boolean isAm(String ldtStr){
        return isAm(ldtStr,EN_TIME_FORMAT);
    }
    public static Boolean isAm(String ldtStr,String format){
        LocalTime ldt = parse(ldtStr,format);
        return isAm(ldt);
    }
    public static Boolean isAm(LocalTime ldt){
        int hour =  ldt.getHour();
        return hour <= 12;
    }

    public static Boolean isPm(String ldtStr){
        return isAm(ldtStr,EN_TIME_FORMAT);
    }
    public static Boolean isPm(String ldtStr,String format){
        LocalTime ldt = parse(ldtStr,format);
        return isPm(ldt);
    }
    public static Boolean isPm(LocalTime ldt){
        int hour =  ldt.getHour();
        return hour > 12;
    }




 
}
