package org.evan.util;

import org.n3r.idworker.Sid;

import java.util.Random;

/**
 * Constant dropping wears the stone.
 * User: Evan
 * Date: 2020/5/22 0022
 * Time: 23:43
 * Description: 我们所有的努力所有的奋斗，都是为了拥有一个美好的未来。和遇见更好的自己。
 * 请把努力当成一种习惯，而不是三分钟热度。每一个你羡慕的收获，都是努力用心拼来的。
 * @author Administrator
 */
public class KeyGenerateUtil {

    /**
     * 一天最大毫秒86400000，最大占用27比特
     * 27+10+11=48位 最大值281474976710655(15字)，YK0XXHZ827(10字)
     * 6位(YYMMDD)+15位，共21位
     *
     * @return 固定21位数字字符串
     */
    public static String get21Num() {
        return Sid.next();
    }

    /**
     * 返回固定16位的字母数字混编的字符串。
     * @return 固定16位的字母数字混编的字符串
     */
    public static String get16LetterAndNum() {
        return Sid.nextShort();
    }

    /**
     * 生成唯一的主键
     * 格式: 时间+随机数
     * @return
     */
    public static synchronized String ge19Num() {
        Integer number = new Random().nextInt(900000) + 100000;
        return String.valueOf(System.currentTimeMillis()) + number;
    }

    /*public static void main(String[] args) {
        String num = get21Num();
        String letterAndNum = get16LetterAndNum();
        String s = ge19Num();
        System.out.println(num);
        System.out.println(letterAndNum);
        System.out.println(s);
    }*/
}
