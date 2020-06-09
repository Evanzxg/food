package org.evan.util;

/**
 * Constant dropping wears the stone.
 * User: Evan
 * Date: 2020/5/22 0022
 * Time: 20:57
 * Description: 我们所有的努力所有的奋斗，都是为了拥有一个美好的未来。和遇见更好的自己。
 * 请把努力当成一种习惯，而不是三分钟热度。每一个你羡慕的收获，都是努力用心拼来的。
 * @author Administrator
 */
public class Result {

    /**
     * 响应码
     */
    private Integer status;

    /**
     * 响应数据
     */
    private Object data;

    /**
     * 响应消息
     */
    private String msg;

    public Result() {

    }

    public Result(Integer status, Object data, String msg) {
        this.status = status;
        this.data = data;
        this.msg = msg;
    }

    public static Result success(){
        return new Result(200, null, "success");
    }

    public static Result success(Object data){
        return new Result(200, data, "success");
    }

    public static Result failed(String msg){
        return new Result(500, null, msg);
    }

    public Integer getstatus() {
        return status;
    }

    public void setstatus(Integer status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
