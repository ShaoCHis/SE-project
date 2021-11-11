package com.example.sebackend.utils;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class HashHelper {

    private static final char[] hexDigits = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
            'e', 'f' };

    public static String computeSha256Hash(String rawData)  {
        try {
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            sha256.update(rawData.getBytes(StandardCharsets.UTF_8));
            byte[] bytes = sha256.digest();
            String encodeStr;
            char[] str = new char[64];
            int k = 0;
            for (int i = 0; i < 32; i++) {
                byte byte0 = bytes[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            encodeStr=new String(str);
            return encodeStr;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String computeMD5Hash(String rawData) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(rawData.getBytes(StandardCharsets.UTF_8));
            byte[] bytes = md5.digest();
            String encodeStr;
            char[] str = new char[32];
            int k = 0;
            for (int i = 0; i < 16; i++) {
                byte byte0 = bytes[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            encodeStr=new String(str);
            return encodeStr;
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }




}
