package com.project.wall.po;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author MercerJR
 * @Data 2020/6/9 13:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Appointment implements Serializable {
    private String appointmentId;

    private String accountId;

    private String username;

    private String title;

    private String content;

    /**
     * 类型（运动、约饭、学习、出行、其他，具体类型码参考文档）
     */
    private Integer type;

    private String img;

    private Long gmtCreate;

    private Long time;

    /**
     * 判断是否超过约定时间了（0没有，1超过）
     */
    private Integer exceed;

    private Integer replyNum;

    private Integer joinNum;

    private static final long serialVersionUID = 1L;
}