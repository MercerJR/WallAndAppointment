package com.project.wall.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author MercerJR
 * @Data 2020/6/2 14:44
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WxParams {
    String appId = HttpInfo.APP_ID;

    String secret = HttpInfo.SECRET;

    String code;

    String grantType = HttpInfo.GRANT_TYPE;

    String username;

    String icon;

    String accountId;
}
