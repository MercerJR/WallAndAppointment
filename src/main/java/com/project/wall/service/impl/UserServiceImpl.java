package com.project.wall.service.impl;

import com.project.wall.dao.UserMapper;
import com.project.wall.dao.UserTokenMapper;
import com.project.wall.data.Message;
import com.project.wall.exception.CustomException;
import com.project.wall.exception.CustomExceptionType;
import com.project.wall.po.User;
import com.project.wall.po.UserToken;
import com.project.wall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.undo.CannotUndoException;

/**
 * @Author MercerJR
 * @Data 2020/4/10 11:32
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper mapper;

    @Autowired
    private UserTokenMapper tokenMapper;

    @Override
    public void insertUser(User user) {
        if(!mapper.insertSelective(user)){
            throw new CustomException(CustomExceptionType.SYSTEM_ERROR, Message.CONTACT_ADMIN);
        }
    }

    @Override
    public boolean existUser(String username) {
        if (mapper.isExistUser(username) != null){
            return true;
        }
        return false;
    }

    @Override
    public boolean existPhone(String telephone) {
        if (mapper.isExistPhone(telephone) != null){
            return true;
        }
        return false;
    }

    @Override
    public void loginUser(String userInfo, String password) {
        if (mapper.loginUser(userInfo, password) == null){
            throw new CustomException(CustomExceptionType.VALIDATE_ERROR,Message.NAME_PASSWORD_ERROR);
        }
    }

    @Override
    public String selectIdByInfo(String userinfo) {
        return mapper.selectIdByInfo(userinfo);
    }

    @Override
    public User findUserById(String accountId) {
        return mapper.selectUserById(accountId);
    }

    @Override
    public void updateInfo(User user) {
        if (!mapper.updateByPrimaryKeySelective(user)){
            throw new CustomException(CustomExceptionType.SYSTEM_ERROR,Message.CONTACT_ADMIN);
        }
    }

    @Override
    public User selectUserByPrimaryKey(Integer id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public void insertUserToken(UserToken userToken) {
        if (!tokenMapper.insert(userToken)){
            throw new CustomException(CustomExceptionType.SYSTEM_ERROR,Message.CONTACT_ADMIN);
        }
    }

    @Override
    public UserToken getUserToken(String accountId) {
        return tokenMapper.selectByPrimaryKey(accountId);
    }

    @Override
    public String existUserToken(String openid) {
        return tokenMapper.selectIdByTokenInfo(openid);
    }

}
