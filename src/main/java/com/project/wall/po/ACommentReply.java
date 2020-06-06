package com.project.wall.po;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author MercerJR
 * @Data 2020/5/26 9:25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ACommentReply implements Serializable {
    private String replyId;

    private String commentId;

    private String accountId;

    private String replyUserId;

    private String content;

    private Long gmtCreate;

    private static final long serialVersionUID = 1L;
}