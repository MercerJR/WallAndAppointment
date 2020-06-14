package com.project.wall.dao;

import com.project.wall.po.UserInfo;import org.apache.ibatis.annotations.Param;

/**
 * @Author MercerJR
 * @Data 2020/6/14 16:02
 */
public interface UserInfoMapper {
    boolean deleteByPrimaryKey(String accountId);

    boolean insert(UserInfo record);

    boolean insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(String accountId);

    boolean updateByPrimaryKeySelective(UserInfo record);

    boolean updateByPrimaryKey(UserInfo record);

    boolean updateUsernameAndIconById(@Param("accountId") String accountId,
                                      @Param("username") String username,@Param("icon")String icon);

    String selectUsernameById(String accountId);

    String selectIconById(String accountId);
}