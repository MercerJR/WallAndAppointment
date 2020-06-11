package com.project.wall.service.impl;

import com.project.wall.data.HttpInfo;
import com.project.wall.data.WxTokenInfo;
import com.project.wall.po.Appointment;
import com.project.wall.po.Wall;
import com.project.wall.service.RedisService;
import com.project.wall.utils.DateFormatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author MercerJR
 * @Data 2020/5/9 12:14
 */
@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private DateFormatUtil dateFormatUtil;

    @Override
    public void insert(String key, String val) {
        redisTemplate.opsForValue().set(key, val);
    }

    @Override
    public boolean existKey(String key) {
        return redisTemplate.hasKey(key);
    }

    @Override
    public String selectStrValByKey(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    @Override
    public void insertHash(String hashKey, String key, Object val) {
        redisTemplate.opsForHash().put(hashKey, key, val);
    }


    @Override
    public WxTokenInfo selectHashValByKey(String hashKey, String key) {
        return (WxTokenInfo) redisTemplate.opsForHash().get(hashKey, key);
    }

    @Override
    public boolean existHashKey(String hashKey, String key) {
        return redisTemplate.opsForHash().hasKey(hashKey, key);
    }


    @Override
    public Long likeWall(String wallId, Long gmtCreat, String accountId) {
        String wallLikekey = HttpInfo.WALL_LIKE + dateFormatUtil.getWallLikeKeyByMiles(gmtCreat) + ":" + wallId;
        String userLikeWallKey = HttpInfo.USER_LIKE_WALL + ":" + accountId;
        String wallLikeCountKey = HttpInfo.WALL_LIKE_COUNT + ":" + wallId;

        RedisAtomicLong entityIdCounter = new RedisAtomicLong(wallLikeCountKey, redisTemplate.getConnectionFactory());
        Long increment = null;
        //如果用户已点赞，那么取消点赞
        if (redisTemplate.opsForSet().isMember(wallLikekey, accountId)) {
            redisTemplate.opsForSet().remove(wallLikekey, accountId);
            redisTemplate.opsForSet().remove(userLikeWallKey, wallId);
            increment = entityIdCounter.decrementAndGet();
        } else {
            redisTemplate.opsForSet().add(wallLikekey, accountId);
            redisTemplate.opsForSet().add(userLikeWallKey, wallId);
            increment = entityIdCounter.incrementAndGet();
        }
        return increment;
    }

    @Override
    public Map getDateKeys(String keyPrefix) {
        Set<String> keys = redisTemplate.keys(keyPrefix + "*");
        int size = keys.size();
        Map<String, Long> map = new HashMap<>(size);
        for (String key : keys) {
            map.put(key, redisTemplate.opsForSet().size(key));
        }
        return map;
    }

    @Override
    public void removeKeysInHash(String hashKey, int num) {
        Set keys = redisTemplate.opsForHash().keys(hashKey);
        Iterator it = keys.iterator();
        for (int i = 0; i < num; i++) {
            redisTemplate.opsForHash().delete(hashKey, it.next());
        }
    }

    @Override
    public List getHash(String hashKey) {
        List list = new ArrayList();
        Set keys = redisTemplate.opsForHash().keys(hashKey);
        for (Object key : keys) {
            list.add(redisTemplate.opsForHash().get(hashKey, key));
        }
        return list;
    }

    @Override
    public void moveHashToAnother(String hashKey1, String hashKey2) {
        Set keys = redisTemplate.opsForHash().keys(hashKey1);
        for (Object key : keys) {
            redisTemplate.opsForHash().put(hashKey2, key, redisTemplate.opsForHash().get(hashKey1, key));
        }
    }

    @Override
    public void removeKey(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public Long joinAppointment(String appointmentId, String accountId) {
        String appointmentJoinKey = HttpInfo.APPOINTMENT_JOIN + ":" + appointmentId;
        String userJoinAppointmentKey = HttpInfo.USER_JOIN_APPOINTMENT + ":" + accountId;
        String appointmentJoinCountKey = HttpInfo.APPOINTMENT_JOIN_COUNT + ":" + appointmentId;

        RedisAtomicLong entityIdCounter = new RedisAtomicLong(appointmentJoinCountKey, redisTemplate.getConnectionFactory());
        Long increment = null;
        //如果已经加入约定，就移除约定
        if (redisTemplate.opsForSet().isMember(appointmentJoinKey, accountId)) {
            redisTemplate.opsForSet().remove(appointmentJoinKey, accountId);
            redisTemplate.opsForSet().remove(userJoinAppointmentKey, appointmentId);
            increment = entityIdCounter.decrementAndGet();
        } else {
            redisTemplate.opsForSet().add(appointmentJoinKey, accountId);
            redisTemplate.opsForSet().add(userJoinAppointmentKey, appointmentId);
            increment = entityIdCounter.incrementAndGet();
        }
        return increment;
    }

    @Override
    public Set getJoinSetByUser(String accountId) {
        String userJoinAppointmentKey = HttpInfo.USER_JOIN_APPOINTMENT + ":" + accountId;
        Set set = redisTemplate.opsForSet().members(userJoinAppointmentKey);
        return set;
    }

    @Override
    public Set getLikeSetByUser(String accountId) {
        String userLikeWallKey = HttpInfo.USER_LIKE_WALL + ":" + accountId;
        Set set = redisTemplate.opsForSet().members(userLikeWallKey);
        return set;
    }

    @Override
    public void getWallLikeCount(List wallList) {
        for (int i = 0; i < wallList.size(); i++) {
            Wall wall = (Wall) wallList.get(i);
            String key = wall.getWallId();
            String redisKey = HttpInfo.WALL_LIKE_COUNT + ":" + key;
            Integer count = redisTemplate.hasKey(redisKey) ?
                    (Integer) redisTemplate.opsForValue().get(redisKey) : 0;
            wall.setLikeNum(count);
        }
    }

    @Override
    public void getWallReplyCount(List wallList) {
        for (int i = 0; i < wallList.size(); i++) {
            Wall wall = (Wall) wallList.get(i);
            String key = wall.getWallId();
            String redisKey = HttpInfo.REPLY_NUM + ":" + key;
            Integer count = redisTemplate.hasKey(redisKey) ?
                    (Integer) redisTemplate.opsForValue().get(redisKey) : 0;
            wall.setReplyNum(count);
        }
    }

    @Override
    public void getAppointmentReplyCount(List appointmentList) {
        for (int i = 0; i < appointmentList.size(); i++) {
            Appointment appointment = (Appointment) appointmentList.get(i);
            String key = appointment.getAppointmentId();
            String redisKey = HttpInfo.REPLY_NUM + ":" + key;
            Integer count = redisTemplate.hasKey(redisKey) ?
                    (Integer) redisTemplate.opsForValue().get(redisKey) : 0;
            appointment.setReplyNum(count);
        }
    }

    @Override
    public void getAppointmentJoinCount(List appointmentList) {
        for (int i = 0; i < appointmentList.size(); i++) {
            Appointment appointment = (Appointment) appointmentList.get(i);
            String key = appointment.getAppointmentId();
            String redisKey = HttpInfo.APPOINTMENT_JOIN_COUNT + ":" + key;
            Integer count = redisTemplate.hasKey(redisKey) ?
                    (Integer) redisTemplate.opsForValue().get(redisKey) : 0;
            appointment.setJoinNum(count);
        }
    }

    @Override
    public Integer getAppointmentJoinCount(String appointmentId) {
        String appointmentJoinCountKey = HttpInfo.APPOINTMENT_JOIN_COUNT + ":" + appointmentId;
        Integer count = redisTemplate.hasKey(appointmentJoinCountKey) ?
                (Integer) redisTemplate.opsForValue().get(appointmentJoinCountKey) : 0;
        return count;
//        return (Integer) redisTemplate.opsForValue().get(appointmentJoinCountKey);
    }

    @Override
    public void deleteWallLikeInfo(Wall wall, String accountId) {
        //----------------------------------这里要改-------------------------------------
        String wallLikekey = HttpInfo.WALL_LIKE + dateFormatUtil.getWallLikeKeyByMiles(Long.valueOf(wall.getGmtCreate())) + ":" + wall.getWallId();
        String userLikeWallKey = HttpInfo.USER_LIKE_WALL + ":" + accountId;
        String wallLikeCountKey = HttpInfo.WALL_LIKE_COUNT + ":" + wall.getWallId();
        redisTemplate.delete(wallLikekey);
        redisTemplate.delete(wallLikeCountKey);
        redisTemplate.opsForSet().remove(userLikeWallKey, wall.getWallId());
    }

    @Override
    public void deleteAppointmentJoinInfo(Appointment appointment, String accountId) {
        String appointmentJoinKey = HttpInfo.APPOINTMENT_JOIN + ":" + appointment.getAppointmentId();
        String userJoinAppointmentKey = HttpInfo.USER_JOIN_APPOINTMENT + ":" + accountId;
        String appointmentJoinCountKey = HttpInfo.APPOINTMENT_JOIN_COUNT + ":" + appointment.getAppointmentId();
        redisTemplate.delete(appointmentJoinKey);
        redisTemplate.delete(appointmentJoinCountKey);
        redisTemplate.opsForSet().remove(userJoinAppointmentKey, appointment.getAppointmentId());
    }

    @Override
    public void increaseReplyNum(String postId) {
        String increaseKey = HttpInfo.REPLY_NUM + ":" + postId;
        redisTemplate.opsForValue().increment(increaseKey);
    }

    @Override
    public void decreaseReplyNum(String postId, Integer decreaseNum) {
        String decreaseKey = HttpInfo.REPLY_NUM + ":" + postId;
        redisTemplate.opsForValue().decrement(decreaseKey, decreaseNum);
    }

    @Override
    public void deleteReplyNum(String postId) {
        redisTemplate.delete(HttpInfo.REPLY_NUM + ":" + postId);
    }

    @Override
    public Integer getWallLikeCount(String wallId) {
        String wallLikeCountKey = HttpInfo.WALL_LIKE_COUNT + ":" + wallId;
        return (Integer) redisTemplate.opsForValue().get(wallLikeCountKey);
    }

    @Override
    public boolean isUserLike(String wallId, String accountId) {
        String userLikeWallKey = HttpInfo.USER_LIKE_WALL + ":" + accountId;
        return redisTemplate.opsForSet().isMember(userLikeWallKey, wallId);
    }


    @Override
    public boolean isUserJoin(String appointmentId, String accountId) {
        String userJoinAppointmentKey = HttpInfo.USER_JOIN_APPOINTMENT + ":" + accountId;
        return redisTemplate.opsForSet().isMember(userJoinAppointmentKey, appointmentId);
    }

    @Override
    public void leftInsertList(String key, String value) {
        redisTemplate.opsForList().leftPush(key,value);
    }

    @Override
    public void leftPopInList(String yesterdayKey, int length) {
        for (int i = 0;i < length;i++){
            redisTemplate.opsForList().leftPop(yesterdayKey);
        }
    }

    @Override
    public void moveListToAnother(String key1, String key2) {
        Long size = redisTemplate.opsForList().size(key1);
        for (int i = 0;i < size ;i++){
            redisTemplate.opsForList().rightPopAndLeftPush(key1, key2);
        }
    }

    @Override
    public List<Object> getList(String key) {
        return redisTemplate.opsForList().range(key,0,-1);
    }

    @Override
    public int getListSize(String key) {
        return Math.toIntExact(redisTemplate.opsForList().size(key));
    }

}
