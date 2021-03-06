package com.yjh.util;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyStrUtil {

    public static String filterEmoji(String txt){
        return filterEmoji(txt,"");
    }
    public static String filterEmoji(String txt,String replace) {
        if (StrUtil.isNotBlank(txt)){
            Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                    Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
            Matcher emojiMatcher = emoji.matcher(txt);
            if (emojiMatcher.find()) {
                txt = emojiMatcher.replaceAll(replace);
                return txt;
            }
        }
        return txt;
    }

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

    public static String toJsonStr(Object obj){
        return JSONObject.toJSONString(obj);
    }
    public static Map<String,Object> parseMap(String obj){
        return JSONObject.parseObject(obj);
    }
    public static List<Object> parseList(String json){
        return  parseList(json,Object.class);
    }
    public static <T> List<T> parseList(String json,Class<T> clazz){
        return  JSONObject.parseArray(json,clazz);
    }
    public static Object parseObj(String json){
        return  parseObj(json,Object.class);
    }
    public static <T> T parseObj(String json,Class<T> clazz){
        return  JSONObject.parseObject(json,clazz);
    }

}
