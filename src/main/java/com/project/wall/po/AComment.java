package com.project.wall.po;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author MercerJR
 * @Data 2020/6/9 13:17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AComment implements Serializable {
    private String commentId;

    private String appointmentId;

    private String accountId;

    private String username;

    private String icon;

    private String content;

    private Long gmtCreate;

    private static final long serialVersionUID = 1L;
}