package com.project.wall.dao;

import com.project.wall.po.WCommentReply;import org.apache.ibatis.annotations.Param;import java.util.List;

/**
 * @Author MercerJR
 * @Data 2020/6/9 22:53
 */
public interface WCommentReplyMapper {
    boolean deleteByPrimaryKey(String replyId);

    boolean insert(WCommentReply record);

    boolean insertSelective(WCommentReply record);

    WCommentReply selectByPrimaryKey(String replyId);

    boolean updateByPrimaryKeySelective(WCommentReply record);

    boolean updateByPrimaryKey(WCommentReply record);

    boolean deleteByIdAndUser(@Param("replyId")String replyId,@Param("accountId")String accountId);

    int deleteByComment(@Param("commentId") String commentId);

    List<WCommentReply> getReplyList(String commentId);

    void updateUsername(@Param("accountId") String accountId,@Param("username") String username);

    void updateReplyUsername(@Param("replyUserId") String replyUserId, @Param("replyUsername") String replyUsername);
}