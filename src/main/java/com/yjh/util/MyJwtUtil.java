package com.yjh.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.*;

/**
 * jwt token 工具类
 */
public class MyJwtUtil {

    /** 私钥 **/
    /** 时长 **/
    private static final Integer  TIME_OUT = 120;
    /**单位（分钟）**/
    private static final Integer unit = Calendar.MINUTE;

    public static String getToken(String username){
        return getToken(TIME_OUT,MyContact.JWTKEY,username);
    }
    public static String getToken(Integer timeout,String username){
        return getToken(timeout,MyContact.JWTKEY,username);
    }
    public static String getToken(Integer timeout,String key,String username){
        //签发时间
        Date start = new Date();
        // 失效时间
        Calendar calendar = Calendar.getInstance();
        calendar.add(unit,timeout);
        Date end = calendar.getTime();

        // 私有声明
        Map<String,Object> map = new HashMap<>();
        map.put("username",username);
        // 唯一标识
        String id = UUID.randomUUID().toString().replace("-","") + username;
        JwtBuilder builder = Jwts.builder()
                .setClaims(map)
                .setId(id)
                .setIssuedAt(start)
                .setSubject(username)
                .signWith(SignatureAlgorithm.HS256, key)
                .setExpiration(end);
        return  builder.compact();
    }


    public static String getSubject(String token) {
       return getSubject(MyContact.JWTKEY,token);
    }
    /**
     * Token的解密
     * @param token 加密后的token
     * @return
     */
    public static String getSubject(String key,String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public static Boolean authToken(String token, String username) {
       return authToken(MyContact.JWTKEY,token,username);
    }
    /**
     * 检验token 是否正确
     * @param token
     * @param username
     * @return
     */
    public static Boolean authToken(String key,String token, String username) {
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token).getBody();
       return claims.getSubject().equals(username);
    }
}
