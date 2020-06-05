package com.yjh.util;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

public class MyRsaUtil {


    private static String publicKey;
    private static String privateKey;

    public static String init(){
        KeyPairGenerator keyPairGenerator;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(1024);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
            publicKey = Base64.encodeBase64String(rsaPublicKey.getEncoded());
            privateKey =  Base64.encodeBase64String(rsaPrivateKey.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return privateKey;
    }

    /**
     * 公钥加密
     * @param content
     * @return
     */
    public static String pubEncrypt(String content){
        try {
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKey));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] result = cipher.doFinal(content.getBytes());
            content =  Base64.encodeBase64String(result);
        } catch (Exception e){}
        return content;
    }

    /**
     * 公钥解密
     * @param content
     * @return
     */
    public static String pubDecrypt(String content){
        byte[] result = null;
        try {
            byte[] res = Base64.decodeBase64(content);
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKey));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            result = cipher.doFinal(res);
        } catch (Exception e){}
       return new String(result);
    }


    /**
     * 私钥解密
     * @param content
     * @return
     */
    public static String priDecrypt(String content){
        byte[] result = null;
        try {
            byte[] res = Base64.decodeBase64(content);
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
            KeyFactory  keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
             result = cipher.doFinal(res);
        } catch (Exception e){}
        return new String(result);
    }


    /**
     * 私钥加密
     * @param content
     * @return
     */
    public static String priEncrypt(String content){
        //私钥加密
        byte[] result = null;
        try {
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
             result = cipher.doFinal(content.getBytes());
        } catch (Exception e){};
        return Base64.encodeBase64String(result);
    }

    public static void main(String[] args) {
        String a = init();
        System.out.println(a);
    }

}
