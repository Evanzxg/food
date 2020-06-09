package org.evan.util;

import org.apache.commons.lang3.StringUtils;

/**
 * Constant dropping wears the stone.
 * User: Evan
 * Date: 2020/6/4 0004
 * Time: 22:07
 * Description: 我们所有的努力所有的奋斗，都是为了拥有一个美好的未来。和遇见更好的自己。
 * 请把努力当成一种习惯，而不是三分钟热度。每一个你羡慕的收获，都是努力用心拼来的。
 * @author Administrator
 */
public class DesensitizationUtil {
    private static final int TWO=2;

    public static String desensitization(String sour){
        if(StringUtils.isBlank(sour)){
            return null;
        }

        int length = sour.length();
        if(length == TWO){
            return sour.replaceFirst(sour.substring(1), "*");
        }
        if(length > TWO){
            return sour.replaceAll(sour.substring(1, length-1), "*");
        }

        return null;
    }
}
