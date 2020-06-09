package org.evan.enums;

/**
 * Constant dropping wears the stone.
 * User: Evan
 * Date: 2020/6/7 0007
 * Time: 13:31
 * Description: 我们所有的努力所有的奋斗，都是为了拥有一个美好的未来。和遇见更好的自己。
 * 请把努力当成一种习惯，而不是三分钟热度。每一个你羡慕的收获，都是努力用心拼来的。
 * @author Administrator
 */
public enum OrderStatusEnum {
    WAIT_PAY(10,"待付款"),
    WAIT_DELIVERY(20,"已付款,待发货"),
    WAIT_RECEIVE(30,"已付款,待收货"),
    SUCCESS(40,"交易成功"),
    CLOSE(50,"交易关闭");

    public Integer code;
    public String value;

    OrderStatusEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }
}

