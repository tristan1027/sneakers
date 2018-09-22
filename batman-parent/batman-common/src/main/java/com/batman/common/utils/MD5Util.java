package com.batman.common.utils;

import java.security.MessageDigest;

/**   
 * @ClassName:  MD5Util   
 * @Description:md5工具类  
 * @author: tristan
 * @date:   2017年9月25日 下午9:10:54   
 *   
 */ 
public class MD5Util {

    private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
            "e", "f" };

    public static String byteArrayToString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));

        }
        return resultSb.toString();
    }

    private static String byteToNumString(byte b) {

        int _b = b;
        if ( _b < 0 ) {
            _b = 256 + _b;
        }
        return String.valueOf(_b);
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if ( n < 0 ) {
            n = 256 + n;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    public static String encode(String origin) {
        String resultString = null;

        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultString = byteArrayToString(md.digest(resultString.getBytes()));
        }
        catch (Exception ex) {

        }
        return resultString;
    }

    public static void main(String[] args) {
        System.out.println(MD5Util.encode("admin"));
    }
}
