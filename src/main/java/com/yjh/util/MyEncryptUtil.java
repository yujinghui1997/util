package com.yjh.util;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import org.apache.logging.log4j.util.ProviderUtil;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.SecureRandom;


public class MyEncryptUtil {
    //密钥 (需要前端和后端保持一致)
    private static final String KEY = "eyJzdWIm5hbWUiOi";
    private static final String ALGORITHMSTR = "AES/ECB/PKCS5Padding";

    public static String AESDecrypt(String encrypt) {
        return AESDecrypt(encrypt, KEY);
    }
    public static String AESEncrypt(String content) { return AESEncrypt(content, KEY); }

    public static String AESEncrypt(String content, String encryptKey) {
        return base64Encode(aesEncryptToBytes(content, encryptKey));
    }

    public static String AESDecrypt(String encryptStr, String decryptKey) {
        return aesDecryptByBytes(base64Decode(encryptStr), decryptKey);
    }


    public static String aesDecryptByBytes(byte[] encryptBytes, String decryptKey) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128);
            Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(), "AES"));
            byte[] decryptBytes = cipher.doFinal(encryptBytes);
            return new String(decryptBytes);
        } catch (Exception e){}
       return null;
    }

    public static byte[] aesEncryptToBytes(String content, String encryptKey)  {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128);
            Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(), "AES"));
            return cipher.doFinal(content.getBytes("utf-8"));
        } catch (Exception e){}
        return null;
    }



    public static String base64Encode(byte[] bytes){
        return Base64.encodeBase64String(bytes);
    }

    public static byte[] base64Decode(String base64Code){
        try {
            if (StringUtils.isEmpty(base64Code)){
                return null;
            }
           return new BASE64Decoder().decodeBuffer(base64Code);
        } catch (Exception e){}
        return null;
    }



}
