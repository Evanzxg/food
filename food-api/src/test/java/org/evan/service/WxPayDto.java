package org.evan.service;

/**
 * @program: api-demo
 * @description:
 * @author: Evan
 * @create: 2020-08-17 15:02
 **/
public class WxPayDto {
    private String body;
    private String out_trade_no;
    //订单总金额，单位为分，只能为整数
    private int total_fee;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public int getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(int total_fee) {
        this.total_fee = total_fee;
    }
}
