package com.project.wall.service;

import com.project.wall.po.User;
import com.project.wall.po.UserInfo;
import com.project.wall.po.UserToken;

/**
 * @Author MercerJR
 * @Data 2020/4/10 11:32
 */
public interface UserService {

    /**
     * 向MySQL中新增一个用户
     */
    void insertUser(User user);

    /**
     * 查询该用户名是否已经存在
     * @return
     */
    boolean existUser(String username);

    /**
     * 查询该手机号是否已经存在
     * @return
     */
    boolean existPhone(String telephone);

    /**
     * 用手机号或者用户名登陆
     */
    void loginUser(String userInfo, String password);

    String selectIdByInfo(String userinfo);

    User findUserById(String accountId);

    void updateInfo(User user);

    User selectUserByPrimaryKey(Integer id);

    void insertUserToken(UserToken userToken);

    UserToken getUserToken(String tokenKey);

    String existUserToken(String openid);

    void updateUsername(String accountId,String username,String icon);

    void createUserInfo(String accountId, String username,String icon);

    String selectUsernameById(String accountId);

    String selectIconById(String accountId);

    void updateUserInfo(UserInfo userInfo);

    UserInfo getUserInfoById(String accountId);
}
