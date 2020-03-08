package com.yjh.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MyLdUtil {

    public static final String EN_DATE_FORMAT = "yyyy-MM-dd";
    public static final String CH_DATE_FORMAT = "yyyy年MM月dd日";

    public static LocalDate now(){return LocalDate.now();}
    public static String getStr(){ return getStr(now()); }
    public static String getStr(LocalDate ldt){ return getStr(ldt,EN_DATE_FORMAT); }
    public static String getStr(LocalDate ldt,String format){ return ldt.format(DateTimeFormatter.ofPattern(format)); }

    public static LocalDate parse(String strLdt){
        return parse(strLdt,EN_DATE_FORMAT);
    }
    public static LocalDate parse(String strLdt,String format){
        return LocalDate.parse(strLdt,DateTimeFormatter.ofPattern(format));
    }

    public static LocalDate plus(Long num, MyLdtUnit unit){
        return plus(now(),num,unit);
    }
    public static String plusStr(Long num, MyLdtUnit unit){
        LocalDate ldt =  plus(now(),num,unit);
        return getStr(ldt);
    }
    public static LocalDate plus(LocalDate ldt, Long num, MyLdtUnit unit){
        switch (unit){
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
        LocalDate mainLdt = parse(mainStr);
        LocalDate viceLdt = parse(viceStr);
        return compare(mainLdt,viceLdt);
    }
    public static Integer compare(LocalDate mainLdt,LocalDate viceLdt){
        if (viceLdt.isEqual(mainLdt)){
            return 0;
        }
        if (viceLdt.isBefore(mainLdt)){
            return -1;
        }
        return 1;
    }


}
