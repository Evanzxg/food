package org.evan.service.pay;


/**
 * @program: Skill
 * @description:
 * @author: Feri
 * @create: 2020-02-28 11:42
 */
public class WxPayDto {
    private String body;
    private String out_trade_no;
    private int total_fee;//单位分

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
