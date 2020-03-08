package com.yjh.util;

import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MyLdUtil {

    public static final String EN_DATE_FORMAT = "yyyy-MM-dd";
    public static final String CH_DATE_FORMAT = "yyyy年MM月dd日";

    public static LocalDate now(){return LocalDate.now();}
    public static String getStr(){
        return getStr(now());
    }
    public static String getStr(String format){
        return getStr(now(),format);
    }
    public static String getStr(LocalDate ldt) {
        return getStr(ldt,EN_DATE_FORMAT); }
    public static String getStr(LocalDate ldt,String format){
        return ldt.format(DateTimeFormatter.ofPattern(format));
    }

    public static LocalDate parse(String strLdt){
        return parse(strLdt,EN_DATE_FORMAT);
    }
    public static LocalDate parse(String strLdt,String format){
        return LocalDate.parse(strLdt,DateTimeFormatter.ofPattern(format));
    }

    public static LocalDate plus(Long num, MyLdtUnit unit){
        return plus(now(),num,unit);
    }
    public static LocalDate plus(String ldStr,Long num,MyLdtUnit unit){
        return plus(ldStr,EN_DATE_FORMAT,num,unit);
    }
    public static LocalDate plus(String ldStr,String format,Long num,MyLdtUnit unit){
        LocalDate ld =  parse(ldStr,format);
        return plus(ld,num,unit);
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

    public static String plusStr(Long num, MyLdtUnit unit){
        LocalDate ldt =  plus(now(),num,unit);
        return getStr(ldt);
    }
    public static String plusStr(String ldStr,Long num,MyLdtUnit unit){
        return plusStr(ldStr,EN_DATE_FORMAT,num,unit);
    }
    public static String plusStr(String ldStr,String format,Long num,MyLdtUnit unit){
        LocalDate ld =  plus(ldStr,format,num,unit);
        return getStr(ld,format);
    }
    public static String plusStr(LocalDate ld,Long num,MyLdtUnit unit){
        return plusStr(ld,EN_DATE_FORMAT,num,unit);
    }
    public static String plusStr(LocalDate ld,String format,Long num,MyLdtUnit unit){
        ld = plus(ld,num,unit);
        return getStr(ld,format);
    }

    public static Integer compare(String mainStr,String viceStr){
        return compare(mainStr,viceStr,EN_DATE_FORMAT);
    }
    public static Integer compare(String mainStr,String viceStr,String bothFormat){
        return compare(mainStr,bothFormat,viceStr,bothFormat);
    }
    public static Integer compare(String mainStr,String mainFormat,String viceStr,String viceFormat){
        LocalDate mainLdt = parse(mainStr,mainFormat);
        LocalDate viceLdt = parse(viceStr,viceFormat);
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

    public static Boolean isRun(String ldtStr){
        return isRun(ldtStr,EN_DATE_FORMAT);
    }
    public static Boolean isRun(String ldtStr, String format){
        LocalDate ldt = parse(ldtStr,format);
        return isRun(ldt);
    }
    public static Boolean isRun(LocalDate ldt){
        return ldt.getYear() % 4 == 0;
    }



}
