package com.project.wall.dao;

import com.project.wall.po.AComment;
import com.project.wall.po.ACommentReply;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author MercerJR
 * @Data 2020/5/26 9:25
 */
public interface ACommentReplyMapper {
    boolean deleteByPrimaryKey(String replyId);

    boolean insert(ACommentReply record);

    boolean insertSelective(ACommentReply record);

    ACommentReply selectByPrimaryKey(String replyId);

    boolean updateByPrimaryKeySelective(ACommentReply record);

    boolean updateByPrimaryKey(ACommentReply record);

    boolean deleteByIdAndUser(@Param("replyId") String replyId,@Param("accountId")String accountId);

    List<ACommentReply> getReplyListInComment(List<AComment> commentList);

    int deleteByComment(@Param("commentId") String commentId);
}