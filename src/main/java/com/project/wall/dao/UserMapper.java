package com.project.wall.dao;

import com.project.wall.po.User;import org.apache.ibatis.annotations.Param;

/**
 * @Author MercerJR
 * @Data 2020/4/20 14:26
 */
public interface UserMapper {
    boolean deleteByPrimaryKey(Integer id);

    boolean insert(User record);

    boolean insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    boolean updateByPrimaryKeySelective(User record);

    boolean updateByPrimaryKey(User record);


    Integer isExistUser(String username);

    Integer isExistPhone(String telephone);

    Integer loginUserByName(@Param("username") String username,
                            @Param("password") String password);

    Integer loginUserByPhone(@Param("telephone") String telephone,
                             @Param("password") String password);

    Integer loginUser(@Param("userInfo") String userInfo,
                      @Param("password") String password);

    String selectIdByInfo(String userinfo);

    User selectUserById(String accountId);
}