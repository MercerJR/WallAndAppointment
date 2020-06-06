package com.project.wall.po;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author MercerJR
 * @Data 2020/5/25 17:52
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AComment implements Serializable {
    private String commentId;

    private String appointmentId;

    private String accountId;

    private String content;

    private Long gmtCreate;

    private static final long serialVersionUID = 1L;
}