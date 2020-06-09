package com.project.wall.po;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author MercerJR
 * @Data 2020/6/9 13:15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Wall implements Serializable {
    private String wallId;

    private String accountId;

    private String username;

    private String title;

    private String content;

    private String img;

    private String label;

    private Long gmtCreat;

    private Long gmtModified;

    private Integer replyNum;

    private Integer likeNum;

    private static final long serialVersionUID = 1L;
}