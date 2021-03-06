package com.project.wall.dao;

import com.project.wall.po.Wall;import org.apache.ibatis.annotations.Param;import java.util.List;

/**
 * @Author MercerJR
 * @Data 2020/6/13 22:32
 */
public interface WallMapper {
    boolean deleteByPrimaryKey(String wallId);

    boolean insert(Wall record);

    boolean insertSelective(Wall record);

    Wall selectByPrimaryKey(String wallId);

    boolean updateByPrimaryKeySelective(Wall record);

    boolean updateByPrimaryKey(Wall record);

    boolean deleteWall(@Param("wallId") String wallId,@Param("accountId") String accountId);

    List<Wall> selectDefaultWallList(@Param("num") Integer num);

    Integer getCount();

    List<Wall> selectWallListByTime(@Param("startTime") Long startTime,
                                    @Param("endTime") Long endTime);

    List<Wall> selectByUser(String accountId);

    void updateUsername(@Param("accountId") String accountId,@Param("username") String username);

    Integer selectNumByUser(String accountId);

    boolean updateLikeCount(@Param("likeCount") Integer likeCount,
                            @Param("replyCount") Integer replyCount, @Param("wallId") String wallId);
}