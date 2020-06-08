package com.project.wall.service;

import com.project.wall.data.CommentResponse;
import com.project.wall.po.WComment;
import com.project.wall.po.WCommentReply;
import com.project.wall.po.Wall;

import java.util.List;

/**
 * @Author MercerJR
 * @Data 2020/4/20 14:50
 */
public interface WallService {

    void publishWall(Wall wall);

    void updateWall(Wall wall);

    void deleteWallById(String wallId,String accountId);

    List getDefaultWallList(Integer num);

    List getWallListByTime(Long startTime,Long endTime);

    Wall getWallById(String wallId);

    void publishComment(WComment comment);

    void deleteComment(String commentId,String accountId);

    void publishReply(WCommentReply reply);

    void deleteReply(String replyId,String accountId);

    int deleteReplyInComment(String commentId);

    String getWallIdByComment(String commentId);

    List<CommentResponse<WComment,WCommentReply>> getCommentListInWall(String wallId);

    List getWallListByUser(String accountId);

}
