package com.project.wall.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author MercerJR
 * @Data 2020/5/10 10:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WxTokenInfo {
    private String openId;
    private String sessionKey;
}
