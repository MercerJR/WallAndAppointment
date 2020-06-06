package com.project.wall.po;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author MercerJR
 * @Data 2020/4/20 14:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    /**
     * 自增id
     */
    private Integer id;

    /**
     * 用户id（用UUID生成）
     */
    private String accountId;

    private String username;

    private String password;

    /**
     * 头像
     */
    private String icon;

    private String telephone;

    private String school;

    /**
     * 墙的发布数量
     */
    private Integer wallPub;

    /**
     * 约吧发布数量
     */
    private Integer appointmentPub;

    /**
     * 用户创建时间
     */
    private Long gmtCreate;

    /**
     * 用户修改时间
     */
    private Long gmtModified;

    private static final long serialVersionUID = 1L;
}