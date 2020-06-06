package com.project.wall.dao;

import com.project.wall.po.Wall;import org.apache.ibatis.annotations.Param;import java.util.List;

/**
 * @Author MercerJR
 * @Data 2020/5/15 12:39
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
}