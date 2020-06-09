package org.evan.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Constant dropping wears the stone.
 * User: Evan
 * Date: 2020/5/22 0022
 * Time: 21:35
 * Description: 我们所有的努力所有的奋斗，都是为了拥有一个美好的未来。和遇见更好的自己。
 * 请把努力当成一种习惯，而不是三分钟热度。每一个你羡慕的收获，都是努力用心拼来的。
 * @author Administrator
 */
@ApiModel(value = "用户对象BO",description = "从客户端传输到后端的封装对象")
public class UserBo {

    @ApiModelProperty(value = "用户名",name = "username",example = "张三",readOnly = true)
    private String username;
    @ApiModelProperty(value = "密码",name = "password",example = "123123",readOnly = true)
    private String password;
    @ApiModelProperty(value = "确认密码",name = "confirmPassword",example = "123123",readOnly = true)
    private String confirmPassword;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
