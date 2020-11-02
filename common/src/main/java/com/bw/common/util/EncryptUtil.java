package com.bw.common.util;

import android.util.Base64;


import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.TreeMap;

public class EncryptUtil {

    //输入字符串，然后返回该字符串的信息摘要
    public static String enrypByMd5(String message) {
        byte[] hash;

        try {
            hash = MessageDigest.getInstance("MD5").digest(
                    message.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10)
                hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }

        return hex.toString();
    }

    //对参数的value进行编码加密
    public static TreeMap<String, String> encryptParamsValueByBase64(TreeMap<String, String> params) {
        TreeMap<String, String> encryptedParams = new TreeMap<>();
        for (String key : params.keySet()) {
            String value = params.get(key);
            byte[] encryptValue = Base64.encode(value.getBytes(), Base64.DEFAULT);
            String encryptStr = new String(encryptValue);
            encryptedParams.put(key, encryptStr);
        }
        return encryptedParams;
    }

    //实现生成json的信息摘要（签名)
    public static String encryptJsonByMd5(JSONObject jParams) {
        StringBuilder sb = new StringBuilder();
        //先排序
        TreeMap<String, String> params = new TreeMap<>();
        Iterator<String> iterator = jParams.keys();
        while (iterator.hasNext()) {
            String key = iterator.next();
            try {
                params.put(key, jParams.get(key).toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //先拼接成字符串
        for (String key : params.keySet()) {
            String value = params.get(key);
            sb.append(key + "=" + value + "&");
        }
        sb.append("encrypt=md5");
        String sign = enrypByMd5(sb.toString());
        return sign;
    }

    //使用Base64对jsonobject里面的参数值进行加密
    public static void encryptJsonByBase64(JSONObject jParams) {
        Iterator<String> iterator = jParams.keys();
        while (iterator.hasNext()) {
            String key = iterator.next();
            String value = null;
            try {
                value = jParams.get(key).toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String encyptValue = new String(Base64.encode(value.getBytes(), Base64.DEFAULT));
            try {
                jParams.put(key, encyptValue);//使用加密后值来替换之前未加密的值
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
