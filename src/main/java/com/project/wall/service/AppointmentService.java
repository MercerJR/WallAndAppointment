package com.project.wall.service;

import com.project.wall.data.CommentResponse;
import com.project.wall.po.AComment;
import com.project.wall.po.ACommentReply;
import com.project.wall.po.Appointment;

import java.util.List;

/**
 * @Author MercerJR
 * @Data 2020/5/20 11:37
 */
public interface AppointmentService {

    void publishAppointment(Appointment appointment);

    void updateAppointment(Appointment appointment);

    void deleteAppointmentById(String appointmentId,String accountId);

    List<Appointment> getAppointmentList(Integer type);

    void publishComment(AComment comment);

    void deleteComment(String commentId,String accountId);

    void publishReply(ACommentReply reply);

    void deleteReply(String replyId,String accountId);

    Appointment getAppointmentById(String appointmentId);

    int deleteReplyInComment(String commentId);

    String getAppointmentIdByComment(String commentId);

    List<CommentResponse<AComment,ACommentReply>> getCommentListInAppointment(String appointmentId);

    List getAppointmentListByUser(String accountId);

    void updateUsername(String accountId, String username);

    Integer getAppointmentNumByUser(String accountId);

    void insertNum(Integer joinCount,Integer replyCount,String appointmentId);
}
