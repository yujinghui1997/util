package com.yjh.util;

import cn.hutool.dfa.WordTree;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;

import java.util.List;
import java.util.Map;

public class MyHttpUtil {


	/*
	 *  工具类依赖
	 *  <dependency>
	 *		<groupId>cn.hutool</groupId>
	 *		<artifactId>hutool-all</artifactId>
	 *		<version>5.0.7</version>
	 *	</dependency>
	 *
	 *  官方文档 https://hutool.cn/docs/#/
	 */


    private static Integer timeOut = 10000;
    private static String SEND_XML = "text/xml";
    private static String SEND_JSON = "application/json";
    private static String CHARSET_UTF8 = "utf-8";


    public static String get(String url) {
        return get(url, null);
    }
    public static String get(String url, Map<String, Object> param) {
        return get(url, param, null);
    }
    public static String get(String url, Map<String, Object> param, Map<String, String> header) {
        HttpRequest request = HttpRequest.get(url);
        if (param != null && param.size() > 0) {
            request.form(param);
        }
        if (header != null && header.size() > 0) {
            for (Map.Entry<String, String> entry : header.entrySet()) {
                request.header(entry.getKey(), entry.getValue());
            }
        }
        request.charset(CHARSET_UTF8);
        request.timeout(timeOut);
        return request.execute().body();
    }


    public static String post(String url) {
        return post(url, null);
    }
    public static String post(String url, Map<String, Object> param) {
        return post(url, param, null);
    }
    public static String post(String url, Map<String, Object> param, Map<String, String> header) {
        HttpRequest request = HttpRequest.post(url);
        if (param != null && param.size() > 0) {
            request.form(param);
        }
        if (header != null && header.size() > 0) {
            for (Map.Entry<String, String> entry : header.entrySet()) {
                request.header(entry.getKey(), entry.getValue());
            }
        }
        request.charset(CHARSET_UTF8);
        request.timeout(timeOut);
        return request.execute().body();
    }
    public static String postJson(String url, Map<String, Object> param) {
        return postJson(url, param, null);
    }
    public static String postJson(String url, Map<String, Object> param,Map<String, String> header) {
        String json = JSONUtil.toJsonStr(param);
        return postJson(url, json, header);
    }
    public static String postJson(String url,String json) {
        return postJson(url,json,null);
    }
    public static String postJson(String url,String json, Map<String, String> header) {
        HttpRequest request = HttpRequest.post(url);
        if (header != null && header.size() > 0) {
            for (Map.Entry<String, String> entry : header.entrySet()) {
                request.header(entry.getKey(), entry.getValue());
            }
        }
        request.timeout(timeOut);
        request.body(json);
        request.charset(CHARSET_UTF8);
        request.contentType(SEND_JSON);
        return request.execute().body();
    }

    public static String postXml(String url, String xml) {
        return postXml(url, xml, null);
    }
    public static String postXml(String url, String xml, Map<String, String> header) {
        HttpRequest request = HttpRequest.post(url);
        if (header != null && header.size() > 0) {
            for (Map.Entry<String, String> entry : header.entrySet()) {
                request.header(entry.getKey(), entry.getValue());
            }
        }
        request.body(xml);
        request.charset(MyHttpUtil.CHARSET_UTF8);
        request.contentType(MyHttpUtil.SEND_XML);
        request.timeout(timeOut);
        return request.execute().body();
    }




}
