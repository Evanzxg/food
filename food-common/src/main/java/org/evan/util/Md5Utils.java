package org.evan.util;

import org.apache.commons.codec.binary.Base64;

import java.security.MessageDigest;

/**
 * Constant dropping wears the stone.
 * User: Evan
 * Date: 2020/5/22 0022
 * Time: 21:38
 * Description: 我们所有的努力所有的奋斗，都是为了拥有一个美好的未来。和遇见更好的自己。
 * 请把努力当成一种习惯，而不是三分钟热度。每一个你羡慕的收获，都是努力用心拼来的。
 */
public class Md5Utils {

    public static String getMd5Str(String strValue) throws Exception{
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        String newstr = Base64.encodeBase64String(md5.digest(strValue.getBytes()));
        return newstr;
    }

    public static void main(String[] args) {
        try {
            String md5Str = getMd5Str("123456");
            System.out.println(md5Str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
