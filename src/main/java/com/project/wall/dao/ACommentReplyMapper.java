package com.project.wall.dao;

import com.project.wall.po.ACommentReply;import org.apache.ibatis.annotations.Param;import java.util.List;

/**
 * @Author MercerJR
 * @Data 2020/6/9 11:47
 */
public interface ACommentReplyMapper {
    boolean deleteByPrimaryKey(String replyId);

    boolean insert(ACommentReply record);

    boolean insertSelective(ACommentReply record);

    ACommentReply selectByPrimaryKey(String replyId);

    boolean updateByPrimaryKeySelective(ACommentReply record);

    boolean updateByPrimaryKey(ACommentReply record);

    boolean deleteByIdAndUser(@Param("replyId") String replyId,@Param("accountId")String accountId);

    List<ACommentReply> getReplyListInComment(String commentId);

    int deleteByComment(@Param("commentId") String commentId);

    void updateUsername(@Param("accountId") String accountId,@Param("username") String username);

    void updateReplyUsername(@Param("replyUserId") String replyUserId, @Param("replyUsername") String replyUsername);
}