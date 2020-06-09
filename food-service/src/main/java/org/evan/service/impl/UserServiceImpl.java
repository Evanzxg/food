package org.evan.service.impl;

import org.evan.bo.UserBo;
import org.evan.enums.Sex;
import org.evan.mapper.UsersMapper;
import org.evan.pojo.Users;
import org.evan.service.UserService;
import org.evan.util.DateUtils;
import org.evan.util.KeyGenerateUtil;
import org.evan.util.Md5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

/**
 * Constant dropping wears the stone.
 * @author : Evan
 * Date: 2020/5/22 0022
 * Time: 20:39
 * Description: 我们所有的努力所有的奋斗，都是为了拥有一个美好的未来。和遇见更好的自己。
 * 请把努力当成一种习惯，而不是三分钟热度。每一个你羡慕的收获，都是努力用心拼来的。
 */
@Service
public class UserServiceImpl implements UserService {
    private static final String DEFAULT_FACE_IMG = "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3968417432,4100418615&fm=26&gp=0.jpg";
    @Autowired
    private UsersMapper usersMapper;

    @Transactional(readOnly = true)
    @Override
    public boolean queryUsernameIsExist(String username) {
        Example usersExample = new Example(Users.class);
        Example.Criteria criteria = usersExample.createCriteria();
        criteria.andEqualTo("username",username);
        Users users = usersMapper.selectOneByExample(usersExample);
        return users == null ? false : true;
    }


    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    @Override
    public Users createUser(UserBo userBo) {
        Users user = new Users();
        //生成id
        String id = KeyGenerateUtil.get16LetterAndNum();
        user.setId(id);
        //设置默认昵称
        user.setNickname(userBo.getUsername());
        //设置用户名
        user.setUsername(userBo.getUsername());
        try {
            //加密密码字符串
            user.setPassword(Md5Utils.getMd5Str(userBo.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //设置用户默认头像
        user.setFace(DEFAULT_FACE_IMG);
        //设置用户默认生日
        user.setBirthday(DateUtils.getOneDay("1900-01-01","yyyy-MM-dd"));
        //默认性别 保密
        user.setSex(Sex.SECRET.code);

        user.setCreatedTime(new Date());
        user.setUpdatedTime(new Date());
        //保存用户
        usersMapper.insert(user);
        return user;
    }

    /**
     * 用户登录,根据用户名和密码查询用户
     * @param username
     * @param password
     * @return
     */
    @Override
    public Users queryUsersForLogin(String username,String password) {
        Example usersExample = new Example(Users.class);
        Example.Criteria criteria = usersExample.createCriteria();
        criteria.andEqualTo("username",username);
        criteria.andEqualTo("password", password);
        Users result = usersMapper.selectOneByExample(usersExample);
        return result;
    }
}
