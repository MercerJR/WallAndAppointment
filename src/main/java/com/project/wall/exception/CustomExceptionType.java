package com.project.wall.exception;

/**
 * @Author MercerJR
 * @Data 2020/4/23 11:13
 */
public enum CustomExceptionType {
    /**
     * 系统内部错误
     */
    SYSTEM_ERROR(500,"系统内部错误"),
    /**
     * 参数校验错误
     */
    VALIDATE_ERROR(404,"参数校验错误"),
    /**
     * 用户身份未验证
     */
    NOT_AUTHENTICATION(511,"用户未验证身份"),
    /**
     * 用户输入冲突
     */
    INPUT_CONFLICT(409,"用户输入冲突"),
    /**
     * 未知错误
     */
    UNKNOWN_ERROR(999,"未知错误"),

    /**
     * 微信code请求登陆错误
     */
    WX_CODE_ERROR(998,"微信code请求登陆错误");

    private Integer code;
    private String typeDesc;


    CustomExceptionType(Integer code, String typeDesc){
        this.code = code;
        this.typeDesc = typeDesc;
    }

    public String getTypeDesc(){
        return this.typeDesc;
    }

    public Integer getCode(){
        return this.code;
    }
}
