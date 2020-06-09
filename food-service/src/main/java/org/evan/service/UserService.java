package org.evan.service;

import org.evan.bo.UserBo;
import org.evan.pojo.Users;

/**
 * Constant dropping wears the stone.
 * User: Evan
 * Date: 2020/5/22 0022
 * Time: 20:31
 * Description: 我们所有的努力所有的奋斗，都是为了拥有一个美好的未来。和遇见更好的自己。
 * 请把努力当成一种习惯，而不是三分钟热度。每一个你羡慕的收获，都是努力用心拼来的。
 * @author Administrator
 */
public interface UserService {

    /**
     * 查询用户名是否已存在
     * @param username
     * @return
     */
    boolean queryUsernameIsExist(String username);

    /**
     * 用户注册
     * @param userBo
     * @return
     */
    Users createUser(UserBo userBo);

    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    Users queryUsersForLogin(String username,String password);
}

