package com.project.wall.dao;

import com.project.wall.po.Appointment;import org.apache.ibatis.annotations.Param;import java.util.List;

/**
 * @Author MercerJR
 * @Data 2020/5/20 17:25
 */
public interface AppointmentMapper {
    boolean deleteByPrimaryKey(String appointmentId);

    boolean insert(Appointment record);

    boolean insertSelective(Appointment record);

    Appointment selectByPrimaryKey(String appointmentId);

    boolean updateByPrimaryKeySelective(Appointment record);

    boolean updateByPrimaryKey(Appointment record);

    boolean deleteAppointment(@Param("appointmentId") String appointmentId,@Param("accountId") String accountId);

    List<Appointment> getAppointmentList(@Param("num")Integer num,@Param("type")Integer type);

    List<String> getAppointmentId(@Param("num")Integer num,@Param("type")Integer type);

    List<Appointment> selectAppointmentByUser(String accountId);
}