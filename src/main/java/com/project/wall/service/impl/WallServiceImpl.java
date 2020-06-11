package com.project.wall.service.impl;

import com.project.wall.dao.WCommentMapper;
import com.project.wall.dao.WCommentReplyMapper;
import com.project.wall.dao.WallMapper;
import com.project.wall.data.CommentResponse;
import com.project.wall.data.Message;
import com.project.wall.exception.CustomException;
import com.project.wall.exception.CustomExceptionType;
import com.project.wall.po.WComment;
import com.project.wall.po.WCommentReply;
import com.project.wall.po.Wall;
import com.project.wall.service.WallService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author MercerJR
 * @Data 2020/4/20 14:51
 */
@Service
public class WallServiceImpl implements WallService {

    @Autowired
    private WallMapper mapper;

    @Autowired
    private WCommentMapper commentMapper;

    @Autowired
    private WCommentReplyMapper replyMapper;

    @Override
    public void publishWall(Wall wall) {
        if (!mapper.insertSelective(wall)) {
            throw new CustomException(CustomExceptionType.SYSTEM_ERROR, Message.CONTACT_ADMIN);
        }
    }

    @Override
    public void updateWall(Wall wall) {
        if (!mapper.updateByPrimaryKeySelective(wall)) {
            throw new CustomException(CustomExceptionType.SYSTEM_ERROR, Message.CONTACT_ADMIN);
        }
    }

    @Override
    public void deleteWallById(String wallId, String accountId) {
        if (!mapper.deleteWall(wallId, accountId)) {
            throw new CustomException(CustomExceptionType.SYSTEM_ERROR, Message.CONTACT_ADMIN);
        }
    }

    @Override
    public List<Wall> getDefaultWallList(Integer num) {
        return mapper.selectDefaultWallList(num);
    }

    @Override
    public List getWallListByTime(Long startTime, Long endTime) {
        return mapper.selectWallListByTime(startTime, endTime);
    }

    @Override
    public Wall getWallById(String wallId) {
        return mapper.selectByPrimaryKey(wallId);
    }

    @Override
    public void publishComment(WComment comment) {
        if (!commentMapper.insertSelective(comment)) {
            throw new CustomException(CustomExceptionType.SYSTEM_ERROR, Message.CONTACT_ADMIN);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteComment(String commentId, String accountId) {
        if (commentMapper.deleteByIdAndUser(commentId, accountId)) {
            throw new CustomException(CustomExceptionType.SYSTEM_ERROR, Message.CONTACT_ADMIN);
        }
    }

    @Override
    public void publishReply(WCommentReply reply) {
        if (!replyMapper.insertSelective(reply)) {
            throw new CustomException(CustomExceptionType.SYSTEM_ERROR, Message.CONTACT_ADMIN);
        }
    }

    @Override
    public void deleteReply(String replyId, String accountId) {
        if (!replyMapper.deleteByIdAndUser(replyId, accountId)) {
            throw new CustomException(CustomExceptionType.SYSTEM_ERROR, Message.CONTACT_ADMIN);
        }
    }

    @Override
    public int deleteReplyInComment(String commentId) {
        return replyMapper.deleteByComment(commentId);
    }

    @Override
    public String getWallIdByComment(String commentId) {
        return commentMapper.selectWallByComment(commentId);
    }

    @Override
    public List<CommentResponse<WComment,WCommentReply>> getCommentListInWall(String wallId) {
        List<WComment> commentList = commentMapper.selectCommentInWall(wallId);
        List<CommentResponse<WComment,WCommentReply>> commentResponseList =
                new ArrayList<>();
        for (int i = 0;i < commentList.size();i++){
            CommentResponse<WComment,WCommentReply> commentResponse = new CommentResponse<>();
            WComment comment = commentList.get(i);
            BeanUtils.copyProperties(comment,commentResponse);
            commentResponse.setComment(comment);
            commentResponse.setReplyList(replyMapper.getReplyList(comment.getCommentId()));
            commentResponseList.add(commentResponse);
        }
        return commentResponseList;
    }

    @Override
    public List getWallListByUser(String accountId){
        return mapper.selectByUser(accountId);
    }

    @Override
    public void updateUsername(String accountId,String username) {
        mapper.updateUsername(accountId,username);
        commentMapper.updateUsername(accountId,username);
        replyMapper.updateUsername(accountId,username);
        replyMapper.updateReplyUsername(accountId,username);
    }

    @Override
    public Integer getWallNumByUser(String accountId) {
        return mapper.selectNumByUser(accountId);
    }

    @Override
    public void insertLikeNum(Integer likeCount, String wallId) {
        if (!mapper.updateLikeCount(likeCount,wallId)){
            throw new CustomException(CustomExceptionType.SYSTEM_ERROR,Message.CONTACT_ADMIN);
        }
    }

}
