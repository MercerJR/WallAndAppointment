package com.project.wall.data;

/**
 * @Author MercerJR
 * @Data 2020/4/13 11:53
 */
public interface HttpInfo {
    String USER_ID_COOKIE = "accountId";
    String USER_SESSION = "user";
    String ICON_PATH = "/uploadIcon/";
    String WALL_IMG_PATH = "/wallImg/";
    String APP_ID = "wxa7176c35bfb97606";
    String SECRET = "d67bf958272bde0b952b29f6216fbc54";
    String GRANT_TYPE = "authorization_code";
    String USER_TOKEN = "userToken";
    Integer DEFAULT_WALL_NUM = 20;
    String WALL_LIKE = "wallLike";
    String WALL_HOT = "wallHot";
    String USER_LIKE_WALL = "userLikeWall";
    String WALL_LIKE_COUNT = "wallLikeCount";
    String USER_WALL_LIKE = "userWallLike";
    Integer HOT_STANDARD = 5;
    String APPOINTMENT_JOIN = "appointmentJoin";
    String USER_JOIN_APPOINTMENT = "userJoinAppointment";
    String APPOINTMENT_JOIN_COUNT = "appointmentJoinCount";
    Integer APPOINTMENT_SHOW_NUM = 10;
    String TOKEN_HEADER = "Authorization";
    String REPLY_NUM = "ReplyNum";
}
