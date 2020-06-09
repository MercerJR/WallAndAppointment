package com.project.wall.controller;

import com.project.wall.data.*;
import com.project.wall.exception.CustomException;
import com.project.wall.exception.CustomExceptionType;
import com.project.wall.po.UserToken;
import com.project.wall.service.AppointmentService;
import com.project.wall.service.UserService;
import com.project.wall.service.WallService;
import com.project.wall.utils.CodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @Author MercerJR
 * @Data 2020/5/9 11:07
 */
@Slf4j
@RestController
@Validated
public class WxLoginController {

    @Autowired
    private CodeUtil codeUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private WallService wallService;

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping(value = "/wxLogin", produces = "application/json")
    public Response wxLogin(@RequestBody WxParams params) {
        WxUserToken wxUserToken = codeUtil.getUserToken(params);
        if (wxUserToken.getErrcode() != 0) {
            throw new CustomException(CustomExceptionType.WX_CODE_ERROR, wxUserToken.getErrmsg());
        }
        //查MySQL，没有就生成新的第三方key
        String accountId = userService.existUserToken(wxUserToken.getOpenid());
        if (accountId != null){
            String username = userService.selectUsernameById(accountId);
            if (!username.equals(params.getUsername())){
                userService.updateUsername(accountId,params.getUsername(),params.getIcon());
                wallService.updateUsername(accountId,params.getUsername());
                appointmentService.updateUsername(accountId,params.getUsername());
            }
            return new Response().success(accountId);
        }else {
            String key = UUID.randomUUID().toString();
            if (userService.getUserToken(key) != null) {
                throw new CustomException(CustomExceptionType.SYSTEM_ERROR, Message.CONTACT_ADMIN);
            }
            UserToken userToken = new UserToken(key,wxUserToken.getOpenid(),wxUserToken.getSession_key());
            userService.insertUserToken(userToken);
            userService.createUserInfo(key,params.getUsername(),params.getIcon());
            return new Response().success(key);
        }
    }
}
