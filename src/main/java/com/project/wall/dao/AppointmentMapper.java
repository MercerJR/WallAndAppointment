package com.project.wall.dao;

import com.project.wall.po.Appointment;import org.apache.ibatis.annotations.Param;import java.util.List;

/**
 * @Author MercerJR
 * @Data 2020/6/9 22:46
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

    void updateUsername(@Param("accountId") String accountId,@Param("username") String username);

    Integer selectNumByUser(String accountId);

    boolean updateJoinNum(@Param("joinNum") Integer joinCount,@Param("appointmentId") String appointmentId);
}