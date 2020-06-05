package com.yjh.util;

import org.springframework.util.DigestUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;


public class MyEncryptUtil {
    private static String key = "eyJhbGciOiJIUzI1NiJ9eyJzdWIiOiJoZWxsbyIsImV4cCI6MTU5MTM3NDE4NCwiaWF0IjoxNTkxMzY2OTg0LCJqdGkiOiJmNWU3MzFjNGE2NTc0MmJkYmFlMDY3ODI2NzllMGYyZWhlbGxvIiwidXNlcm5hbWUiOiJoZWxsbyJ9hZTMUcPxxou7gaX9f5Yg1Hxhtewa2E6UtCMka77B1Vc";

    public static final String base64Encode(String base){
        BASE64Encoder encoder =  new BASE64Encoder();
        return encoder.encode(base.getBytes());
    }

    public static final String base64Decode(String encoder){
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] buf = decoder.decodeBuffer(encoder);
            return new String(buf,"UTF-8");
        } catch (Exception e) {
            return null;
        }
    }



    public static String AESEncode(String content){
        return AESEncode(key,content);
    }
    public static String AESDecode(String content){
        return AESDecode(key,content);
    }

    public static String AESEncode(String encodeRules, String content) {
        try {
            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            keygen.init(128, new SecureRandom(encodeRules.getBytes()));
            SecretKey original_key = keygen.generateKey();
            byte[] raw = original_key.getEncoded();
            SecretKey key = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] byte_encode = content.getBytes("utf-8");
            byte[] byte_AES = cipher.doFinal(byte_encode);
            String aesEncode = new BASE64Encoder().encode(byte_AES).toString();
            return aesEncode;
        } catch (Exception e) { }
        return null;
    }


    public static String AESDecode(String encodeRules, String content) {
        try {
            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            keygen.init(128, new SecureRandom(encodeRules.getBytes()));
            SecretKey original_key = keygen.generateKey();
            byte[] raw = original_key.getEncoded();
            SecretKey key = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] byte_content = new BASE64Decoder().decodeBuffer(content);
            byte[] byte_decode = cipher.doFinal(byte_content);
            String AES_decode = new String(byte_decode, "utf-8");
            return AES_decode;
        } catch (Exception e) { }
        return null;
    }

    public static String getMd5(String txt){
        return DigestUtils.md5DigestAsHex(txt.getBytes()).toUpperCase();
    }

}
