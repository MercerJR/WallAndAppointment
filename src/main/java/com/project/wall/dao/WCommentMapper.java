package com.project.wall.dao;

import com.project.wall.po.WComment;import com.project.wall.po.Wall;import org.apache.ibatis.annotations.Param;import java.util.List;

/**
 * @Author MercerJR
 * @Data 2020/6/9 13:17
 */
public interface WCommentMapper {
    boolean deleteByPrimaryKey(String commentId);

    boolean insert(WComment record);

    boolean insertSelective(WComment record);

    WComment selectByPrimaryKey(String commentId);

    boolean updateByPrimaryKeySelective(WComment record);

    boolean updateByPrimaryKey(WComment record);

    boolean deleteByIdAndUser(@Param("commentId") String commentId, @Param("accountId") String accountId);

    List<WComment> getCommentList(List<Wall> wallList);

    String selectWallByComment(String commentId);

    List<WComment> selectCommentInWall(String wallId);

    void updateUsername(@Param("accountId") String accountId,@Param("username") String username);
}