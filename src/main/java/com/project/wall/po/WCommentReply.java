package com.project.wall.po;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author MercerJR
 * @Data 2020/6/9 10:57
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WCommentReply implements Serializable {
    private String replyId;

    private String commentId;

    private String accountId;

    private String username;

    private String replyUserId;

    private String replyUsername;

    private String content;

    private Long gmtCreate;

    private static final long serialVersionUID = 1L;
}