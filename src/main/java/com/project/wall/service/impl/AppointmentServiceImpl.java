package com.project.wall.service.impl;

import com.project.wall.dao.ACommentMapper;
import com.project.wall.dao.ACommentReplyMapper;
import com.project.wall.dao.AppointmentMapper;
import com.project.wall.data.HttpInfo;
import com.project.wall.data.Message;
import com.project.wall.exception.CustomException;
import com.project.wall.exception.CustomExceptionType;
import com.project.wall.po.AComment;
import com.project.wall.po.ACommentReply;
import com.project.wall.po.Appointment;
import com.project.wall.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author MercerJR
 * @Data 2020/5/20 11:44
 */
@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentMapper mapper;

    @Autowired
    private ACommentMapper commentMapper;

    @Autowired
    private ACommentReplyMapper replyMapper;

    @Override
    public void publishAppointment(Appointment appointment) {
        if (!mapper.insertSelective(appointment)) {
            throw new CustomException(CustomExceptionType.SYSTEM_ERROR, Message.CONTACT_ADMIN);
        }
    }

    @Override
    public void updateAppointment(Appointment appointment) {
        if (!mapper.updateByPrimaryKeySelective(appointment)) {
            throw new CustomException(CustomExceptionType.SYSTEM_ERROR, Message.CONTACT_ADMIN);
        }
    }

    @Override
    public void deleteAppointmentById(String appointmentId,String accountId) {
        if (!mapper.deleteAppointment(appointmentId,accountId)){
            throw new CustomException(CustomExceptionType.SYSTEM_ERROR,Message.CONTACT_ADMIN);
        }
    }

    @Override
    public List<Appointment> getAppointmentList(Integer type) {
        return mapper.getAppointmentList(HttpInfo.APPOINTMENT_SHOW_NUM,type);
    }

    @Override
    public void publishComment(AComment comment) {
        if (!commentMapper.insertSelective(comment)){
            throw new CustomException(CustomExceptionType.SYSTEM_ERROR,Message.CONTACT_ADMIN);
        }
    }

    @Override
    public void deleteComment(String commentId,String accountId) {
        if (!commentMapper.deleteByIdAndAccount(commentId, accountId)){
            throw new CustomException(CustomExceptionType.SYSTEM_ERROR,Message.CONTACT_ADMIN);
        }
    }

    @Override
    public void publishReply(ACommentReply reply) {
        if (!replyMapper.insertSelective(reply)){
            throw new CustomException(CustomExceptionType.SYSTEM_ERROR,Message.CONTACT_ADMIN);
        }
    }

    @Override
    public void deleteReply(String replyId, String accountId) {
        if (!replyMapper.deleteByIdAndUser(replyId, accountId)){
            throw new CustomException(CustomExceptionType.SYSTEM_ERROR,Message.CONTACT_ADMIN);
        }
    }

    @Override
    public List getReplyListInComment(List commentList) {
        return replyMapper.getReplyListInComment(commentList);
    }

    @Override
    public Appointment getAppointmentById(String appointmentId) {
        return mapper.selectByPrimaryKey(appointmentId);
    }

    @Override
    public int deleteReplyInComment(String commentId) {
        return replyMapper.deleteByComment(commentId);
    }

    @Override
    public String getAppointmentIdByComment(String commentId) {
        return commentMapper.selectAppointmentByComment(commentId);
    }

    @Override
    public List getCommentListInAppointment(String appointmentId) {
        return commentMapper.selectCommentInAppointment(appointmentId);
    }

    @Override
    public List getAppointmentListByUser(String accountId){
        return mapper.selectAppointmentByUser(accountId);
    }
}