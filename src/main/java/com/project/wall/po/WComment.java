package com.project.wall.po;

import java.io.Serializable;

import com.project.wall.data.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

/**
 * @Author MercerJR
 * @Data 2020/5/27 9:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WComment implements Serializable {
    private String commentId;

    private String wallId;

    @NotEmpty
    private String accountId;

    @NotEmpty(message = Message.CONTENT_NULL)
    private String content;

    private Long gmtCreate;

    private static final long serialVersionUID = 1L;
}