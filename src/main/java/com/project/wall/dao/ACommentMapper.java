package com.project.wall.dao;

import com.project.wall.po.AComment;
import com.project.wall.po.Appointment;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * @Author MercerJR
 * @Data 2020/6/9 13:17
 */
public interface ACommentMapper {
    boolean deleteByPrimaryKey(String commentId);

    boolean insert(AComment record);

    boolean insertSelective(AComment record);

    AComment selectByPrimaryKey(String commentId);

    boolean updateByPrimaryKeySelective(AComment record);

    boolean updateByPrimaryKey(AComment record);

    boolean deleteByIdAndAccount(@Param("commentId") String commentId,
                                 @Param("accountId") String accountId);

    List<AComment> getCommentList(List<Appointment> appointmentList);

    String selectAppointmentByComment(String commentId);

    List<AComment> selectCommentInAppointment(String appointmentId);

    void updateUsername(@Param("accountId") String accountId,@Param("username") String username);
}