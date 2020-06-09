package com.project.wall.data;

import com.project.wall.po.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author MercerJR
 * @Data 2020/6/9 20:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoResponse {

    private UserInfo userInfo;

    private Integer wallNum;

    private Integer appointmentNum;

}
