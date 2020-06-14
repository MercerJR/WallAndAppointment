package com.project.wall.po;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author MercerJR
 * @Data 2020/6/14 16:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo implements Serializable {
    private String accountId;

    private String username;

    private String icon;

    private Integer gender;

    private Integer age;

    /**
     * 邮箱
     */
    private String email;

    private String qqNumber;

    private String telephone;

    /**
     * 学校id
     */
    private Integer schoolId;

    /**
     * 个人介绍
     */
    private String personIntroduction;

    /**
     * 爱好
     */
    private String hobbies;

    /**
     * 家乡
     */
    private String hometown;

    /**
     * 星座
     */
    private String constellation;

    /**
     * 属相
     */
    private String animalSign;

    private static final long serialVersionUID = 1L;
}