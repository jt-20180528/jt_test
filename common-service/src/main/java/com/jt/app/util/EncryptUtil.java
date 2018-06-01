package com.jt.app.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Auther: Administrator
 * @Date: 2018/5/19 18:37
 * @Description: 加密类
 */
public class EncryptUtil {

    /**
     * 生成32位md5码
     * @returnE
     */
    public static String md5Encode(String password) {
        StringBuffer buffer = new StringBuffer();
        try {
            // 得到一个信息摘要器
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] result = digest.digest(password.getBytes());

            // 把每一个byte 做一个与运算 0xff;
            for (byte b : result) {
                // 与运算
                int number = b & 0xff;// 加盐
                String str = Integer.toHexString(number);
                if (str.length() == 1) {
                    buffer.append("0");
                }
                buffer.append(str);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        // 标准的md5加密后的结果
        return buffer.toString();
    }

    ;
}
