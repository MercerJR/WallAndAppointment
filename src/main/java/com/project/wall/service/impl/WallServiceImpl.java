package com.project.wall.service.impl;

import com.project.wall.dao.WCommentMapper;
import com.project.wall.dao.WCommentReplyMapper;
import com.project.wall.dao.WallMapper;
import com.project.wall.data.Message;
import com.project.wall.exception.CustomException;
import com.project.wall.exception.CustomExceptionType;
import com.project.wall.po.WComment;
import com.project.wall.po.WCommentReply;
import com.project.wall.po.Wall;
import com.project.wall.service.WallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public List getReplyListInComment(List commentList) {
        return replyMapper.getReplyList(commentList);
    }

    @Override
    public String getWallIdByComment(String commentId) {
        return commentMapper.selectWallByComment(commentId);
    }

    @Override
    public List getCommentListInWall(String wallId) {
        return commentMapper.selectCommentInWall(wallId);
    }

    @Override
    public List getWallListByUser(String accountId){
        return mapper.selectByUser(accountId);
    }

}
