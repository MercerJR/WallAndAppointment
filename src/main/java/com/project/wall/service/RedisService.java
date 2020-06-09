package com.project.wall.service;

import com.project.wall.data.WxTokenInfo;
import com.project.wall.po.Appointment;
import com.project.wall.po.Wall;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author MercerJR
 * @Data 2020/5/9 12:11
 */
public interface RedisService {
    void insert(String key, String val);

    boolean existKey(String key);

    String selectStrValByKey(String key);

    void insertHash(String hashKey,String key,Object val);

    WxTokenInfo selectHashValByKey(String hashKey,String key);

    boolean existHashKey(String hashKey,String key);

    Long likeWall(String wallId,Long gmtCreat,String accountId);

    Map getDateKeys(String keyPrefix);

    void removeKeysInHash(String hashKey,int num);

    List<Wall> getHash(String hashKey);

    void moveHashToAnother(String hashKey1, String hashKey2);

    void removeKey(String key);

    Long joinAppointment(String appointmentId,String accountId);

    Set getJoinSetByUser(String accountId);

    Set getLikeSetByUser(String accountId);

    void getWallLikeCount(List wallList);

    void getWallReplyCount(List wallList);

    void getAppointmentReplyCount(List wallList);

    void getAppointmentJoinCount(List appointmentList);

    void deleteWallLikeInfo(Wall wall,String accountId);

    void deleteAppointmentJoinInfo(Appointment appointment, String accountId);

    void increaseReplyNum(String postId);

    void decreaseReplyNum(String postId,Integer decreaseNum);

    void deleteReplyNum(String postId);

    Integer getWallLikeCount(String wallId);

    boolean isUserLike(String wallId, String accountId);

    Integer getAppointmentJoinCount(String appointmentId);

    boolean isUserJoin(String appointmentId, String accountId);
    
    void leftInsertList(String key,String value);

    void leftPopInList(String yesterdayKey, int length);

    void moveListToAnother(String Key1, String Key2);

    List<Object> getList(String key);

    int getListSize(String yesterdayKey);
}
