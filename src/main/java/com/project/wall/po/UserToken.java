package com.project.wall.po;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author MercerJR
 * @Data 2020/6/1 12:25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserToken implements Serializable {
    private String accountId;

    private String openId;

    private String sessionKey;

    private static final long serialVersionUID = 1L;
}