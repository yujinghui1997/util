package com.yjh.util;

import cn.hutool.core.util.StrUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyStrUtil {

    public static String getStr(String str,String regular){
        Pattern p = Pattern.compile(regular);
        Matcher m =  p.matcher(str);
        StringBuilder sb = new StringBuilder();
        while (m.find()){
            sb.append(m.group());
        }
        str =  sb.toString();
        str = str.replace(" ","");
        return str;
    }
    public static String getChTxt(String txt){
        String regular = "[\\u4e00-\\u9fa5]+";
        return getStr(txt,regular);
    }
    public static String getEnTxt(String txt){
        String regular = "[A-Za-z]{0,}";
        return getStr(txt,regular);
    }
    public static String getChAndEngAndNumTxt(String txt){
        String regular = "[\\u4e00-\\u9fa5a-zA-A0-9_]+";
        return getStr(txt,regular);
    }

    public static String format(String str,Object... value){
        return StrUtil.format(str,value);
    }


}
