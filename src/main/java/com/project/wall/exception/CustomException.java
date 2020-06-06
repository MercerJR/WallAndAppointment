package com.project.wall.exception;

/**
 * @Author MercerJR
 * @Data 2020/4/23 11:43
 */
public class CustomException extends RuntimeException {

    private Integer code;
    private String message;

    public CustomException(CustomExceptionType exceptionType,String message){
        this.code = exceptionType.getCode();
        this.message = message;
    }

    @Override
    public String getMessage(){
        return this.message;
    }

    public Integer getCode(){
        return this.code;
    }
}
