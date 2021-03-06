package com.project.wall.po;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author MercerJR
 * @Data 2020/6/13 22:32
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

    private String gmtCreate;

    private Integer replyNum;

    private Integer likeNum;

    private Integer show;

    private static final long serialVersionUID = 1L;
}