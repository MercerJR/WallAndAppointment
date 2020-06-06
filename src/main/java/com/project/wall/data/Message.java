package com.project.wall.data;

/**
 * @Author MercerJR
 * @Data 2020/4/10 14:50
 */
public interface Message {
    String USER_IS_EXISTED = "该用户已被注册";
    String PHONE_IS_EXISTED = "该手机号已被注册";
    String UNKNOWN_ERROR = "未知错误，操作失败";
    String USERNAME_NULL = "用户名不能为空";
    String PASSWORD_NULL = "密码不能为空";
    String USER_INFO_NULL = "用户名/手机号不能为空";
    String NAME_PASSWORD_ERROR = "用户名或密码输入错误";
    String ICON_EMPTY = "未选择头像";
    String PASSWORD_UNEQUAL = "两次密码输入不相同";
    String INPUT_NULL = "输入不能为空";
    String PASSWORD_LIMIT = "密码长度应在8到30位之间";
    String TITLE_NULL = "标题不能为空";
    String CONTENT_NULL = "内容不能位空";
    String TYPE_NULL = "类型不能为空";
    String NOT_LOGIN = "您还没有登陆";
    String CONTACT_ADMIN = "系统内部错误，请联系管理员";
    String UPLOAD_IMG_FAIL = "图片上传失败";
    String WX_CODE_EMPTY = "用户登陆凭证(code)为空";
    String SERIALIZE_ERROR = "序列化错误";
    String TIME_SET_ERROR = "当前时间已超过约定时间";
    String NO_PERMISSION = "对不起，您没有权限";
    String TITLE_OR_CONTENT_EMPTY = "标题或内容为空";
    String CONTENT_OR_TYPE_EMPTY = "内容或类型为空";
}
