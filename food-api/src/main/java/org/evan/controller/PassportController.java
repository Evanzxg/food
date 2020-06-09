package org.evan.controller;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.evan.bo.UserBo;
import org.evan.pojo.Users;
import org.evan.service.UserService;
import org.evan.util.CookieUtils;
import org.evan.util.Md5Utils;
import org.evan.util.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Constant dropping wears the stone.
 * User: Evan
 * Date: 2020/5/22 0022
 * Time: 20:47
 * Description: 我们所有的努力所有的奋斗，都是为了拥有一个美好的未来。和遇见更好的自己。
 * 请把努力当成一种习惯，而不是三分钟热度。每一个你羡慕的收获，都是努力用心拼来的。
 *
 * @author Administrator
 */
@Api(value = "用户注册登录", tags = {"用户注册登录相关接口"})
@RestController
@RequestMapping("passport")
public class PassportController {
    final static Logger logger = LoggerFactory.getLogger(PassportController.class);

    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户名是否存在", notes = "用户名是否存在", httpMethod = "GET")
    @GetMapping("usernameIsExist")
    public Result usernameIsExist(@RequestParam String username) {
        //1.判断用户名是否为空
        if (StringUtils.isBlank(username)) {
            return Result.failed("用户名为空！");
        }

        //2.查找用户明是否存在
        boolean isExist = userService.queryUsernameIsExist(username);
        if (isExist) {
            return Result.failed("用户名已存在！");
        }

        //3.返回成功
        return Result.success();

    }


    /**
     * 用户注册
     *
     * @return
     */
    @ApiOperation(value = "用户注册", notes = "用户注册", httpMethod = "POST")
    @PostMapping("regist")
    public Result regist(@RequestBody UserBo userBo,
                         HttpServletRequest req, HttpServletResponse rep) {
        String username = userBo.getUsername();
        String password = userBo.getPassword();
        String confirmPassword = userBo.getConfirmPassword();

        //1.判断用户名和密码不能为空
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return Result.failed("用户名或密码不能为空！");
        }
        //2.验证密码长度
        if (password.length() < 6) {
            return Result.failed("密码长度小于6！");
        }
        //3.判断两次密码是否一致
        if (!password.equals(confirmPassword)) {
            return Result.failed("两次输入密码不一致！");
        }
        //4.查询用户是否存在
        boolean isExist = userService.queryUsernameIsExist(username);
        if (isExist) {
            return Result.failed("用户名已存在！");
        }
        //5.注册用户
        Users user = userService.createUser(userBo);

        //6.设置COOKIE
        String userStr = JSON.toJSONString(user);
        CookieUtils.setCookie(req,rep,"user",userStr);

        //TODO: 生成用户token,存入Redis会话
        //TODO: 同步购物车数据
        return Result.success(user);
    }

    @ApiOperation(value = "用户登录", notes = "用户登录", httpMethod = "POST")
    @PostMapping("login")
    public Result login(@RequestBody UserBo userBo,
                        HttpServletRequest req, HttpServletResponse rep) throws Exception {
        String username = userBo.getUsername();
        String password = userBo.getPassword();

        //1.判断用户名和密码不能为空
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return Result.failed("用户名或密码不能为空！");
        }
        //2.注册用户
        Users user = userService.queryUsersForLogin(username, Md5Utils.getMd5Str(password));
        if(user == null){
            return Result.failed("用户名或密码不正确!");
        }

        user = this.setNullProperty(user);

        //3.设置COOKIE
        String userStr = JSON.toJSONString(user);
        CookieUtils.setCookie(req,rep,"user",userStr);
        //TODO: 生成用户token,存入Redis会话
        //TODO: 同步购物车数据

        return Result.success(user);
    }

    private Users setNullProperty(Users user){
        user.setPassword(null);
        user.setMobile(null);
        user.setRealname(null);
        user.setCreatedTime(null);
        user.setUpdatedTime(null);
        user.setBirthday(null);
        user.setEmail(null);
        return user;
    }

    /**
     * @description     退出登录
     * @date 2020/5/29
     * @param userId: 用户ID
     * @param request:
     * @param response:
     * @return org.evan.util.Result
     * @author evan
     */
    @ApiOperation(value = "退出登录", notes = "退出登录", httpMethod = "POST")
    @PostMapping("logout")
    public Result logout(@RequestParam String userId,HttpServletRequest request,HttpServletResponse response){
        //清除用户相关cookie信息
        CookieUtils.deleteCookie(request,response,"user");

        //TODO: 清空购物车
        //TODO: 分布式会话中需要清除用户数据
        return Result.success();
    }
}
