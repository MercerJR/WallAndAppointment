package com.project.wall.dao;

import com.project.wall.po.UserToken;
import org.apache.ibatis.annotations.Param;

/**
 * @Author MercerJR
 * @Data 2020/6/1 12:25
 */
public interface UserTokenMapper {
    boolean deleteByPrimaryKey(String accountId);

    boolean insert(UserToken record);

    boolean insertSelective(UserToken record);

    UserToken selectByPrimaryKey(String accountId);

    boolean updateByPrimaryKeySelective(UserToken record);

    boolean updateByPrimaryKey(UserToken record);

    String selectIdByTokenInfo(String openid);
}