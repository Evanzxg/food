package org.evan.enums;

/**
 * Constant dropping wears the stone.
 * User: Evan
 * Date: 2020/5/22 0022
 * Time: 23:25
 * Description: 我们所有的努力所有的奋斗，都是为了拥有一个美好的未来。和遇见更好的自己。
 * 请把努力当成一种习惯，而不是三分钟热度。每一个你羡慕的收获，都是努力用心拼来的。
 */
public enum CommentLevel {
    GOOD(1,"好评"),
    NORMAL(2,"中评"),
    BAD(3,"差评");

    public final Integer code;
    public final String value;

    CommentLevel(Integer code, String value) {
        this.code = code;
        this.value = value;
    }}
